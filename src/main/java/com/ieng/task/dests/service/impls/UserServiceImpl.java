package com.ieng.task.dests.service.impls;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ieng.task.dests.dtos.auth.UserDTO;
import com.ieng.task.dests.enumerations.ErrorCode;
import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.model.Destination;
import com.ieng.task.dests.model.User;
import com.ieng.task.dests.repository.UserRepository;
import com.ieng.task.dests.response.Message;
import com.ieng.task.dests.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent())
			return user.get();
		
		return null;
	}

	@Override
	public void create(UserDTO userDTO) {
		
		User existing = getUserByEmail(userDTO.getEmail());
		
		if(existing == null) {
			User user = new User();
			user.setUsername(userDTO.getUsername());
			user.setEmail(userDTO.getEmail());
			System.out.println(userDTO.getPassword());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			
			userRepository.save(user);
		}
	}
	
	@Override
	public void createTempUsers() {
		
		UserDTO user1 = new UserDTO();
		user1.setEmail("user1@gmail.com");
		user1.setPassword("user123");
		user1.setUsername("Temp User 1");
		
		create(user1);
		
		UserDTO user2 = new UserDTO();
		user2.setEmail("user2@gmail.com");
		user2.setPassword("user123");
		user2.setUsername("Temp User 2");
		
		create(user2);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public Set<Destination> getFavouriteDestinations(User user) {
		User u = getUserByEmail(user.getEmail());
		
		return u.getFavouriteDestinations();
	}
	
	
}
