package com.ieng.task.dests.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ieng.task.dests.advice.ResponseEnvelope;
import com.ieng.task.dests.constant.Constant;
import com.ieng.task.dests.dtos.destination.CreateDestinationDTO;
import com.ieng.task.dests.dtos.destination.UpdateDestinationDTO;
import com.ieng.task.dests.enumerations.ErrorCode;
import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.model.User;
import com.ieng.task.dests.response.Message;
import com.ieng.task.dests.security.SecurityUtils;
import com.ieng.task.dests.service.DestinationService;
import com.ieng.task.dests.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/destinations")
public class DestinationController extends BaseController {

	@Autowired
	DestinationService destinationService;
	
	@PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@Valid @RequestPart CreateDestinationDTO payload, @RequestPart(required=false) MultipartFile file) throws Exception {
		
		if(file != null && !Utils.isAllowedMediaType(file.getContentType())) {
			throw new BusinessException(Message.value("message.file.type.not.supported"), "",
					ErrorCode.INVALID_REQUEST.name());
		}
		
		destinationService.create(payload, file);
		
		return new ResponseEntity<ResponseEnvelope>(ResponseEnvelope.builder().success(true).result(Message.value("message.destination.create.success")).build(),
				HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@Valid @RequestPart UpdateDestinationDTO payload, @RequestPart(required=false) MultipartFile file) throws Exception {

		destinationService.update(payload, file);
		
		return new ResponseEntity<ResponseEnvelope>(
				ResponseEnvelope.builder().success(true).result(Message.value("message.destination.update.success")).build(),
				HttpStatus.OK);
	}
	
	@PutMapping(value = "/mark-favourite")
	public ResponseEntity<?> markFavourite(@RequestParam Long id) throws Exception {

		User user = getCurrentUser();
		destinationService.markFavouriteDestination(id,user);
		
		return new ResponseEntity<ResponseEnvelope>(
				ResponseEnvelope.builder().success(true).result(Message.value("message.destination.marked.favourite.success")).build(),
				HttpStatus.OK);
	}
	
	@PutMapping(value = "/unmark-favourite")
	public ResponseEntity<?> UnmarkFavourite(@RequestParam Long id) throws Exception {

		User user = getCurrentUser();
		destinationService.UnmarkFavouriteDestination(id,user);
		
		return new ResponseEntity<ResponseEnvelope>(
				ResponseEnvelope.builder().success(true).result(Message.value("message.destination.un.marked.favourite.success")).build(),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam() Long id) {

		destinationService.delete(id);
		
		return new ResponseEntity<ResponseEnvelope>(
				ResponseEnvelope.builder().success(true).result(Message.value("message.destination.delete.success")).build(),
				HttpStatus.OK);
	}
	
	@GetMapping("/listing")
	public ResponseEntity<?> getDestinationList(@RequestParam(name = Constant.REQ_PARAM_Q) final Optional<String> query,
			@RequestParam(name = Constant.REQ_PARAM_PAGE) final Integer pageNumber,
			@RequestParam(name = Constant.REQ_PARAM_SIZE) final Integer pageSize,
			@RequestParam(name = Constant.REQ_PARAM_SORT) final Optional<String> sortBy,
			@RequestParam(name = Constant.REQ_PARAM_ORDER) final Optional<String> sortOrder) throws BusinessException {
		
		log.debug("REST request to get Destination Listing : {}", query, pageNumber, pageSize, sortBy, sortOrder);
		
		return new ResponseEntity<>(ResponseEnvelope.builder().success(true).result(Message.value("message.destination.get.success"))
				.data(destinationService.getDestinationListings(query, pageNumber, pageSize, sortBy, sortOrder)).build(),
				HttpStatus.OK);
	}
}
