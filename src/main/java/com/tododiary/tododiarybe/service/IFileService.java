package com.tododiary.tododiarybe.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.entity.File;


public interface IFileService {
	
	public File save(MultipartFile file, String folder);

	public File findById(String id);

	public Resource getResource(String uri);
}
