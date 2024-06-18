package com.demo.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.blogapp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) {
		String name = file.getOriginalFilename();
		   
		// Full path
		   
		   String randomID = UUID.randomUUID().toString();
		   String fileName = randomID.concat(name.substring(name.lastIndexOf('.')));
		
		   String filepath = path+File.separator+fileName;
		// create folder if not created
		   File file2 = new File(path);
		
		   if(!file2.exists()) {
			   file2.mkdir();
		   }
	
		   try {
				Files.copy(file.getInputStream(), Paths.get(filepath));
		    } catch (IOException e) {
		        
		        e.printStackTrace(); // Example: Print the stack trace for debugging
		        return "Error occurred while copying the file";
		    }
         
		   return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		 String fullpath = path+File.separator+fileName;
		   
		   InputStream inputStream = new FileInputStream(fullpath);
		   // db logic to return input stream   
		return inputStream;
	}
   
	
}
