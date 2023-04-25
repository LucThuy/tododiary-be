package com.tododiary.tododiarybe.service;

import org.springframework.web.multipart.MultipartFile;


public interface IFileService {
	
	public String save(MultipartFile file, String folder);
}
