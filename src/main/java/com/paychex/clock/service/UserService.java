package com.paychex.clock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paychex.clock.domain.User;
import com.paychex.clock.repo.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		
		return users;
	}
	
	public User getUser(Integer id) {
		return userRepository.findOne(id);
	}
	
	public void addUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}
	
	public void deleteUser(Integer id) {
		userRepository.delete(id);
	}
}
