package com.ieng.task.dests.dtos.destination;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateDestinationDTO {

	@NotNull
	private String title;
	
	@NotNull
	private String locationName;
	
}
