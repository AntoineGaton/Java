package dev.dawsonvaught.javaredbeltexam2.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dev.dawsonvaught.javaredbeltexam2.models.LoginUser;
import dev.dawsonvaught.javaredbeltexam2.models.User;
import dev.dawsonvaught.javaredbeltexam2.services.ShowService;
import dev.dawsonvaught.javaredbeltexam2.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userServ;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
				
		if (session.getAttribute("uuid") != null) {
			return "redirect:/home";
		}

		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "/users/login.jsp";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {
		
		if (session.getAttribute("uuid") != null) {
			return "redirect:/shows";
		}

		userServ.register(newUser, result);

		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "/users/login.jsp";
		}

		session.setAttribute("uuid", newUser.getId());

		return "redirect:/shows";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
			HttpSession session) {
		
		if (session.getAttribute("uuid") != null) {
			return "redirect:/shows";
		}

		User user = userServ.login(newLogin, result);

		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "/users/login.jsp";
		}

		session.setAttribute("uuid", user.getId());

		return "redirect:/shows";
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("uuid");
		return "redirect:/";
	}
}