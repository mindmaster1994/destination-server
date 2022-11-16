package com.ieng.task.dests.dtos.destination;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateDestinationDTO {

	@NotNull
	private Long id;
	
	private String title;
	
	private String locationName;
	
}
