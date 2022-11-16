package com.ieng.task.dests.service.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ieng.task.dests.dtos.auth.UserDTO;
import com.ieng.task.dests.enumerations.ErrorCode;
import com.ieng.task.dests.exception.BusinessException;
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
			throw new BusinessException(Message.value("message.user.email.already.exists"), "",
					ErrorCode.USER_ALREADY_EXISTS.name());
		}
		
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		userRepository.save(user);
	}
	
}
