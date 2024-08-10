package com.example.capstone.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {
    
	private final userRepository userRepository;
	
	@Autowired
	public userService(userRepository usersRepository) {
		this.userRepository = usersRepository;
	}

	public List <users> getUsers(){
		return  userRepository.findAll();
	}
	public Optional<users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void addNewUser(users user , String passwordConfirm) throws Exception { //add a newusers saved in the repository
		Optional<users> userOptional = userRepository.findByEmail(user.getEmail());
		if(userOptional.isPresent()){
			throw new Exception (user.getEmail() + " Already Exist"); //throws a condition if email already save in db
		}
		if(!user.getPassword().equals(passwordConfirm)){
			throw new Exception("Password don't match!");
		}
		userRepository.save(user);
    }

	public Optional<users> loginUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
	
}
