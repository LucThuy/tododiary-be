package com.tododiary.tododiarybe.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.entity.File;
import com.tododiary.tododiarybe.exception.ApiException;
import com.tododiary.tododiarybe.service.impl.FileService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
		fileService.save(file, "");

		return new ResponseEntity<>("Uploaded the file successfully", HttpStatus.OK);
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> download(@PathVariable String id) {
		File file = fileService.findById(id);
		Resource resource = fileService.getResource(file.getUri());
		
		if (resource == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "File is not exist");
		}
		
		InputStreamResource inputStreamResource = null;
		try {
			inputStreamResource = new InputStreamResource(resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String contentType = file.getType();
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType)).body(inputStreamResource);
	}
}
