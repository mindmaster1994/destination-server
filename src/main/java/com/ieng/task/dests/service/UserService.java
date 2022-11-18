package com.ieng.task.dests.service;

import java.util.Set;

import com.ieng.task.dests.dtos.auth.UserDTO;
import com.ieng.task.dests.model.Destination;
import com.ieng.task.dests.model.User;

public interface UserService {
	
	public User getUserByEmail(String email);
	
	public void create(UserDTO user);
	
	public void updateUser(User user);
	
	public void createTempUsers();
	
	public Set<Destination> getFavouriteDestinations(User user);
	
}
