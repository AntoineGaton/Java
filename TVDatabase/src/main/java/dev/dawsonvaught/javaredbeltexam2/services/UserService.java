package dev.dawsonvaught.javaredbeltexam2.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import dev.dawsonvaught.javaredbeltexam2.models.LoginUser;
import dev.dawsonvaught.javaredbeltexam2.models.User;
import dev.dawsonvaught.javaredbeltexam2.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User register(User newUser, BindingResult result) {
    	
    	if (result.hasErrors()) {
			return null;
		}
        
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	
    	if (potentialUser.isPresent()) {
    		result.rejectValue("email", "In Use", "This email is already registered!");
    		return null;
    	}
    	
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	    return null;
    	}
    	
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	
        return userRepo.save(newUser);
    }
    public User login(LoginUser newLoginObject, BindingResult result) {
        
    	if (result.hasErrors()) {
			return null;
		}
        
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
    	
    	if (!potentialUser.isPresent()) {
    		result.rejectValue("email", "Not in Use", "This email is not registered!");
    		return null;
    	}
    	
    	if(!BCrypt.checkpw(newLoginObject.getPassword(), potentialUser.get().getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	    return null;
    	}
    	
        return potentialUser.get();
    }
    
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepo.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
}