package com.ieng.task.dests.service.impls;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ieng.task.dests.dtos.destination.CreateDestinationDTO;
import com.ieng.task.dests.dtos.destination.UpdateDestinationDTO;
import com.ieng.task.dests.dtos.interfaces.GetDestinationListing;
import com.ieng.task.dests.enumerations.ErrorCode;
import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.model.Destination;
import com.ieng.task.dests.model.User;
import com.ieng.task.dests.repository.DestinationRepository;
import com.ieng.task.dests.response.Message;
import com.ieng.task.dests.service.DestinationService;
import com.ieng.task.dests.service.FileService;
import com.ieng.task.dests.service.UserService;
import com.ieng.task.dests.websocket.NotificationService;

@Service
public class DestinationServiceImpl implements DestinationService {

	@Autowired
	DestinationRepository destinationRepository;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotificationService notificationService;
	
	@Override
	public Destination getDestinationById(Long id) {
		Optional<Destination> destination = destinationRepository.findById(id);
		if(destination.isPresent()) {
			return destination.get();
		}
		return null;
	}

	@Override
	public void create(CreateDestinationDTO create, MultipartFile file) throws Exception {
		
		Destination destination = new Destination();
		destination.setTitle(create.getTitle());
		destination.setLocationName(create.getLocationName());
		
		if(file != null)
			destination.setImage(fileService.uploadFile(file));
		
		destinationRepository.save(destination);
	}

	@Override
	public void update(UpdateDestinationDTO update, MultipartFile file) throws Exception {
		Destination destination = getDestinationById(update.getId());
		
		if(destination == null)
			throw new BusinessException(Message.value("message.destination.not.found"), "",
					ErrorCode.NOT_FOUND.name());
		
		destination.setTitle(update.getTitle());
		destination.setLocationName(update.getLocationName());
		
		if(file != null)
			destination.setImage(fileService.uploadFile(file));
		
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

	@Override
	public void markFavouriteDestination(Long id, User user) {
		
		Destination destination = getDestinationById(id);
		
		if(destination == null) {
			throw new BusinessException(Message.value("message.destination.not.found"), "",
					ErrorCode.NOT_FOUND.name());
		}
		
		Set<Destination> destinations = userService.getFavouriteDestinations(user);
		
		if(destinations == null) {
			destinations = new HashSet<Destination>();
		}
		
		destinations.add(destination);		
		
		user.setFavouriteDestinations(destinations);
		
		this.notificationService.sendNotifications(user.getUsername(), destination);
		
		userService.updateUser(user);
	}
	
	@Override
	public void UnmarkFavouriteDestination(Long id, User user) {
		
		Destination destination = getDestinationById(id);
		
		if(destination == null) {
			throw new BusinessException(Message.value("message.destination.not.found"), "",
					ErrorCode.NOT_FOUND.name());
		}
		
		Set<Destination> destinations = userService.getFavouriteDestinations(user);
		
		Predicate<Destination> toBeRemoved = (item) -> item.getId().equals(id);
		
		destinations.removeIf(toBeRemoved);
				
		user.setFavouriteDestinations(destinations);
		
		userService.updateUser(user);
	}

}
