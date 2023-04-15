package com.ieng.task.dests.dtos.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface GetDestinationListing {

	@JsonProperty("id")
	public Long getId();

	@JsonProperty("title")
	public String getTitle();

	@JsonProperty("locationName")
	public String getLocationName();
	
	@JsonProperty("image")
	public String getImage();

}
