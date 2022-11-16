package com.ieng.task.dests.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ieng.task.dests.dtos.destination.CreateDestinationDTO;
import com.ieng.task.dests.dtos.destination.UpdateDestinationDTO;
import com.ieng.task.dests.dtos.interfaces.GetDestinationListing;
import com.ieng.task.dests.model.Destination;

public interface DestinationService {
	
	public Destination getDestinationById(Long id);
	
	public void create(CreateDestinationDTO create);
	
	public void update(UpdateDestinationDTO update);
	
	public void delete(Long id);
	
	public Page<GetDestinationListing> getDestinationListings(Optional<String> query, Integer pageNumber, Integer pageSize,
			Optional<String> sortBy, Optional<String> sortOrder);
}
