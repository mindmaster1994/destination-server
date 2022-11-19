package com.ieng.task.dests.dtos.auth;

import java.util.List;

import lombok.Data;

@Data
public class FavouriteRequestMessage {
	private List<Long> favourites;
}
