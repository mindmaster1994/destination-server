package com.ieng.task.dests.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.ieng.task.dests.dtos.auth.FavouriteRequestMessage;
import com.ieng.task.dests.websocket.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;

	@MessageMapping("/store")
	public void storeFavouriteDestinations(@Payload FavouriteRequestMessage message, Principal user) {
		log.info("Data received from: {}", user.getName());
		
		this.notificationService.addUserName(user.getName(), message.getFavourites());
	}
	
}
