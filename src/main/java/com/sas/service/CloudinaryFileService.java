package com.sas.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryFileService {
	public Map Upload(MultipartFile C_file, String fileName);

}
