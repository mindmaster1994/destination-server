package com.ieng.task.dests.service.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ieng.task.dests.dtos.destination.CreateDestinationDTO;
import com.ieng.task.dests.dtos.destination.UpdateDestinationDTO;
import com.ieng.task.dests.dtos.interfaces.GetDestinationListing;
import com.ieng.task.dests.enumerations.ErrorCode;
import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.model.Destination;
import com.ieng.task.dests.repository.DestinationRepository;
import com.ieng.task.dests.response.Message;
import com.ieng.task.dests.service.DestinationService;


@Service
public class DestinationServiceImpl implements DestinationService {

	@Autowired
	DestinationRepository destinationRepository;
	
	@Override
	public Destination getDestinationById(Long id) {
		Optional<Destination> destination = destinationRepository.findById(id);
		if(destination.isPresent()) {
			return destination.get();
		}
		return null;
	}

	@Override
	public void create(CreateDestinationDTO create) {
		
		Destination destination = new Destination();
		destination.setTitle(create.getTitle());
		destination.setLocationName(create.getLocationName());
		
		destinationRepository.save(destination);
	}

	@Override
	public void update(UpdateDestinationDTO update) {
		Destination destination = getDestinationById(update.getId());
		
		if(destination == null)
			throw new BusinessException(Message.value("message.destination.not.found"), "",
					ErrorCode.NOT_FOUND.name());
		
		destination.setTitle(update.getTitle());
		destination.setLocationName(update.getLocationName());
		
		destinationRepository.save(destination);
	}
	
	public Page<GetDestinationListing> getDestinationListings(Optional<String> query, Integer pageNumber, Integer pageSize,
			Optional<String> sortBy, Optional<String> sortOrder) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, sortBy(sortBy.get(), sortOrder.get()));
		
		return destinationRepository.getDestinationListings(paging);
	}
	
	public Sort sortBy(String sortBy, String sortOrder) {
		return "desc".equals(sortOrder) ? JpaSort.unsafe(Direction.DESC, "(" + sortBy + ")")
				: JpaSort.unsafe(Direction.ASC, "(" + sortBy + ")");
	}

	@Override
	public void delete(Long id) {
		Destination destination = getDestinationById(id);
		
		if(destination == null)
			throw new BusinessException(Message.value("message.destination.not.found"), "",
					ErrorCode.NOT_FOUND.name());
		
		destinationRepository.delete(destination);
	}

}
