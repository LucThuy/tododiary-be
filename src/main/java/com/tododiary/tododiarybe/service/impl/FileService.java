package com.tododiary.tododiarybe.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.entity.File;
import com.tododiary.tododiarybe.repository.IFileRepository;
import com.tododiary.tododiarybe.service.IFileService;

@Service
public class FileService implements IFileService {

	@Autowired
	private IFileRepository fileRepository;

	private static final Path ROOT = Paths.get("files");

	@Override
	public File save(MultipartFile file, String folder) {
		Path folderPath = Paths.get(folder);
		if (!Files.exists(ROOT.resolve(folderPath))) {
			try {
				Files.createDirectories(ROOT.resolve(folderPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String fileName = UUID.randomUUID().toString();
		fileName += file.getContentType().replace("/", ".");
		Path filePath = ROOT.resolve(folderPath).resolve(fileName);
		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String uri = filePath.toString().replace("\\", "/");
		String type = file.getContentType();

		File newFile = File.builder().uri(uri).type(type).build();
		fileRepository.save(newFile);

		return newFile;
	}

	@Override
	public File findById(String id) {

		return fileRepository.findById(id).get();

	}

	@Override
	public Resource getResource(String uri) {
		try {
			return new UrlResource(Paths.get(uri).toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
