package com.ieng.task.dests.utils;

public class Utils {
	public static boolean isAllowedMediaType(String fileType) {
		
    	switch(fileType)
    	{
	    	case "image/png":{
	    		return true;
	    	}
	    	case "image/jpeg":{
	    		return true;
	    	}
	    	case "image/jpg":{
	    		return true;
	    	}
    	}
		return false;
	    
	}
}
