package com.ieng.task.dests.service;

import com.ieng.task.dests.dtos.auth.UserDTO;
import com.ieng.task.dests.model.User;

public interface UserService {
	
	public User getUserByEmail(String email);
	
	public void create(UserDTO user);
	
}
