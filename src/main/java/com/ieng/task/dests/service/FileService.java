package com.ieng.task.dests.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

    public String uploadFile(MultipartFile file) throws Exception
    {
        try {
        	
        	String fileName = "img_" + String.valueOf(System.currentTimeMillis()) + getExtension(file.getContentType());
            Path copyLocation = Paths.get("C:\\Temp\\upload");
            if (!Files.exists(copyLocation)) {
                Files.createDirectories(copyLocation);
            }
            Path filePath = copyLocation.resolve(StringUtils.cleanPath(fileName));
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("File Not Found");
        }
    }
    
    private String getExtension(String fileType){
    	switch(fileType)
    	{
	    	case "image/png":{
	    		return ".png";
	    	}
	    	case "image/jpeg":{
	    		return ".jpeg";
	    	}
	    	case "image/jpg":{
	    		return ".jpg";
	    	}
    	}
		return "";
    }
}
