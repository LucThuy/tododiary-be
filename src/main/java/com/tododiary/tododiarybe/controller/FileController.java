package com.tododiary.tododiarybe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.service.impl.FileService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile file){
		fileService.save(file,"");
		
		return new ResponseEntity<>("Uploaded the file successfully", HttpStatus.OK);
	}
	
}
