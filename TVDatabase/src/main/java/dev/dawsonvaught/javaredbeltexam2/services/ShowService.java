package dev.dawsonvaught.javaredbeltexam2.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import dev.dawsonvaught.javaredbeltexam2.models.Show;
import dev.dawsonvaught.javaredbeltexam2.models.User;
import dev.dawsonvaught.javaredbeltexam2.repositories.ShowRepository;

@Service
public class ShowService {

	private final ShowRepository showRepository;
	
	public ShowService(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}
	
	public ArrayList<Show> all() {
		return showRepository.findAll();
	}
	
	public Show create(Show show) {
		return showRepository.save(show);
	}
	
	public Show find(Long id) {
		Optional<Show> optionalshow = showRepository.findById(id);
		if (optionalshow.isPresent()) {
			return optionalshow.get();
		} else {
			return null;
		}
	}
	
	public Show find(Show newShow) {
		ArrayList<Show> shows = showRepository.findAll();
		
		for (Show show : shows) {
			if (show.getTitle().equals(newShow.getTitle())) {
				return show;
			}
		}
		
		return null;
	}
	
	public void validate(Show newShow, BindingResult result, boolean isNew) {
		if (result.hasErrors()) {
			return;
		}
		
		if (this.find(newShow) != null) {
			if (isNew) {				
				result.rejectValue("title", "Matches", "This show is already listed!");
			}
			
			else if (!this.find(newShow.getId()).getTitle().equals(newShow.getTitle())) {
				result.rejectValue("title", "Matches", "This show is already listed!");
			}
		}
	}
	
	public Show update(Show show) {
		return showRepository.save(show);
	}
	
	 public void delete(Show show) {
	        showRepository.delete(show);
	 }
	
	public void delete(long id) {
		showRepository.deleteById(id);
	}
}