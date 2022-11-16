package com.ieng.task.dests.dtos.auth;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	@Size(max = 45)
	private String username;

	@Email
	@Size(max = 45)
	private String email;

	@JsonIgnore
	private String password;
}
