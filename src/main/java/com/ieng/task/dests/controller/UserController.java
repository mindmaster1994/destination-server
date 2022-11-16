package com.ieng.task.dests.controller;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ieng.task.dests.advice.ResponseEnvelope;
import com.ieng.task.dests.dtos.auth.LoginRequestDTO;
import com.ieng.task.dests.dtos.auth.UserDTO;
import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.model.User;
import com.ieng.task.dests.response.JwtResponse;
import com.ieng.task.dests.response.Message;
import com.ieng.task.dests.security.jwt.JwtUtils;
import com.ieng.task.dests.security.service.UserDetailsImpl;
import com.ieng.task.dests.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		User user = userService.getUserByEmail(userDetails.getEmail());
		
		return new ResponseEntity<ResponseEnvelope>(ResponseEnvelope.builder().success(true).result(new JwtResponse(jwt, user)).build(),
				HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {

		userService.create(userDTO);

		return new ResponseEntity<ResponseEnvelope>(
				ResponseEnvelope.builder().success(true).result(Message.value("message.user.create.success")).build(),
				HttpStatus.CREATED);
	}
}
