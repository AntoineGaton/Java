package dev.dawsonvaught.javaredbeltexam2.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import dev.dawsonvaught.javaredbeltexam2.models.Rating;
import dev.dawsonvaught.javaredbeltexam2.models.Show;
import dev.dawsonvaught.javaredbeltexam2.models.User;
import dev.dawsonvaught.javaredbeltexam2.services.ShowService;
import dev.dawsonvaught.javaredbeltexam2.services.UserService;

@Controller
public class ShowController {

	@Autowired
	ShowService showService;
	@Autowired
	UserService userService;

	@GetMapping("/shows")
	public String shows(HttpSession session, Model model) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		Long uuid = (Long) session.getAttribute("uuid");
		model.addAttribute("user", userService.findById(uuid));
		model.addAttribute("shows", showService.all());

		return "/shows/showAll.jsp";
	}

	@GetMapping("/shows/new")
	public String newShowView(Model model, HttpSession session) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		model.addAttribute("show", new Show());

		return "/shows/new.jsp";
	}

	@PostMapping("/shows/new/submit")
	public String register(@Valid @ModelAttribute("show") Show newShow, BindingResult result, Model model,
			HttpSession session) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		showService.validate(newShow, result, true);

		if (result.hasErrors()) {
			return "/shows/new.jsp";
		}

		newShow.setUser(userService.findById((long) session.getAttribute("uuid")));
		showService.create(newShow);

		return "redirect:/shows";
	}

	@GetMapping("/shows/{id}")
	public String showOne(Model model, HttpSession session, @PathVariable("id") long id, @ModelAttribute("rating") Rating rating) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		Show show = showService.find(id);
		model.addAttribute("show", show);
		model.addAttribute("user", userService.findById((long) session.getAttribute("uuid")));

		return "/shows/showOne.jsp";
	}

	@GetMapping("/shows/{id}/edit")
	public String editShow(Model model, HttpSession session, @PathVariable("id") Long id) {

		Show show = showService.find(id);

		if (show.getUser().getId() != (long) session.getAttribute("uuid")) {
			return "redirect:/shows";
		}

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		model.addAttribute("show", showService.find(id));
		model.addAttribute("newShow", new Show());

		return "/shows/edit.jsp";
	}

	@PutMapping("/shows/{id}/update")
	public String editUpdate(HttpSession session, @PathVariable("id") Long id,
			@Valid @ModelAttribute("newShow") Show newShow, BindingResult result, Model model) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}

		Show show = showService.find(id);

		if (show.getUser().getId() != (long) session.getAttribute("uuid")) {
			return "redirect:/shows";
		}

		showService.validate(newShow, result, false);

		if (result.hasErrors()) {
			model.addAttribute("show", newShow);
			return "/shows/edit.jsp";
		}

		newShow.setId(id);
		newShow.setUser(userService.findById((long) session.getAttribute("uuid")));
		showService.update(newShow);

		return "redirect:/shows";
	}

	@DeleteMapping("/shows/{id}/delete")
	public String destroy(@PathVariable("id") long id, HttpSession session) {

		Show show = showService.find(id);

		if (show.getUser().getId() == (long) session.getAttribute("uuid")) {
			showService.delete(id);
		}

		return "redirect:/shows";
	}

	@PutMapping("/shows/{id}/review")
	public String review(@PathVariable("id") long id, HttpSession session, @Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model) {

		if (session.getAttribute("uuid") == null) {
			return "redirect:/";
		}
		
		Show show = showService.find(id);
		
		if (result.hasErrors()) {
			model.addAttribute("show", show);
			model.addAttribute("user", userService.findById((long) session.getAttribute("uuid")));
			model.addAttribute("rating", rating);
			System.out.println(result.getAllErrors());
			return "/shows/showOne.jsp";
		}

		User user = userService.findById((long) session.getAttribute("uuid"));
		
		List<Rating> filteredList = show.getRatings().stream()
			.filter(ratingObj -> ratingObj.getShow() == show && ratingObj.getUser() == user)
			.collect(Collectors.toList());
		
		if (filteredList.size() != 0) {
			System.out.println("Update");
			filteredList.get(0).setRating(rating.getRating());
			showService.update(show);
		}
		
		else {
			show.getRatings().add(new Rating(user, show, rating.getRating()));
			System.out.println(show.getRatings());
			showService.create(show);
		}
		
		return "redirect:/shows/" + id;
	}

}
