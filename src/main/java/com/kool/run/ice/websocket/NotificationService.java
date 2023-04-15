package com.ieng.task.dests.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ieng.task.dests.model.Destination;

@Service
public class NotificationService {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
    private static final String DESTINATION = "/favourite/destination";
    
    private Map<String, List<Long>> userFavs = new HashMap<String, List<Long>>();
    
    public void sendNotifications(String userName, Destination destination) {
    	Long id = destination.getId();
    	for(String key : userFavs.keySet()) {
    		
    		List<Long> favs = userFavs.get(key);
    		Iterator<Long> iterator = favs.iterator();
    		while(iterator.hasNext()) {
    			Long destinationId = iterator.next();
    			if(id.equals(destinationId)) {
    				simpMessagingTemplate.convertAndSendToUser(key, DESTINATION,
                  userName + " likes " + destination.getTitle() + " too.");
    			}
    		}

    	}
    }
    
    public void addUserName(String username, List<Long> list) {
    	userFavs.put(username, list);
    }
    
}	
