package com.ieng.task.dests.websocket;

import java.security.Principal;

import lombok.Data;

@Data
public class UserPrincipal implements Principal {

	private String uuid;
	
	
	public UserPrincipal(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.uuid;
	}
	
}
