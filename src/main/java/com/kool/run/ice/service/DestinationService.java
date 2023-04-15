package com.ieng.task.dests.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ieng.task.dests.dtos.destination.CreateDestinationDTO;
import com.ieng.task.dests.dtos.destination.UpdateDestinationDTO;
import com.ieng.task.dests.dtos.interfaces.GetDestinationListing;
import com.ieng.task.dests.model.Destination;
import com.ieng.task.dests.model.User;

public interface DestinationService {
	
	public Destination getDestinationById(Long id);
	
	public void create(CreateDestinationDTO create, MultipartFile file) throws Exception;
	
	public void update(UpdateDestinationDTO update, MultipartFile file) throws Exception;
	
	public void delete(Long id);
	
	public void markFavouriteDestination(Long id, User user);
	
	public void UnmarkFavouriteDestination(Long id, User user);
	
	public Page<GetDestinationListing> getDestinationListings(Optional<String> query, Integer pageNumber, Integer pageSize,
			Optional<String> sortBy, Optional<String> sortOrder);
}
