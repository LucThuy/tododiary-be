package com.tododiary.tododiarybe.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.service.IFileService;

@Service
public class FileService implements IFileService {

	private static final Path ROOT = Paths.get("files");

	@Override
	public String save(MultipartFile file, String folder) {
		Path folderPath = Paths.get(folder);
		if (!Files.exists(ROOT.resolve(folderPath))) {
			try {
				Files.createDirectories(ROOT.resolve(folderPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String fileName = UUID.randomUUID().toString();
		Path filePath = ROOT.resolve(folderPath).resolve(fileName);
		try (OutputStream os = Files.newOutputStream(filePath)) {
			os.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return filePath.toString().replace("\\", "/");
	}

}
