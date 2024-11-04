package com.example.capstone.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.capstone.IdGeneratorService;

@Service
public class userService {
    
	private final userRepository userRepository;
	private final IdGeneratorService idGeneratorService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public userService(userRepository usersRepository, IdGeneratorService idGeneratorService, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = usersRepository;
		this.idGeneratorService = idGeneratorService;
		this.passwordEncoder = passwordEncoder;
	}

	public List <users> getUsers(){
		return  userRepository.findAll();
	}
	public Optional<users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void addNewUser(users user , String passwordConfirm) throws Exception { //add a newusers saved in the repository
		user.setRole("client");
		Optional<users> userOptional = userRepository.findByEmail(user.getEmail());
		if(userOptional.isPresent()){
			throw new Exception (user.getEmail() + " Already Exist"); //throws a condition if email already save in db
		}
		if(!user.getPassword().equals(passwordConfirm)){
			throw new Exception("Password don't match!");
		}

		user.setId(idGeneratorService.generateUserId());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);
	}
    

	public Optional<users> loginUser(String email, String password) {
        Optional<users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional;
        }
        return Optional.empty(); 
	}
}
