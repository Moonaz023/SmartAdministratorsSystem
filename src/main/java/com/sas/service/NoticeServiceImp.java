package com.sas.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.NoticeEntity;
import com.sas.repository.NoticeRepository;

@Service
public class NoticeServiceImp implements NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	private static final String UPLOAD_DIR = "src\\main\\resources\\static\\notics";
	public final String UPLOAD_DIR2 = new ClassPathResource("/static/notics").getFile().getAbsolutePath();
	public NoticeServiceImp()throws IOException {
		
	}
//	private static final String UPLOAD_DIR = "notics";

	@Override
	public void insertNotice(NoticeEntity notice, MultipartFile file) {
		// TODO Auto-generated method stub
		try {
			String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
			notice.setFileName(originalFileName);
			Path uploadPath = Paths.get(UPLOAD_DIR2);

			// If the directory doesn't exist, create it
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Save the file to the server
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, uploadPath.resolve(originalFileName), StandardCopyOption.REPLACE_EXISTING);
			}

			System.out.println("Notice file uploaded successfully: " + originalFileName);

		} catch (IOException e) {
			e.printStackTrace();

		}
		noticeRepository.save(notice);
	}

	@Override
	public List<NoticeEntity> fetchNotice() {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<NoticeEntity> listOfNotice = noticeRepository.findAll(sort);
		return listOfNotice;
	}

	private static final Map<String, MediaType> CONTENT_TYPE_MAP = new HashMap<>();

	static {
		CONTENT_TYPE_MAP.put("pdf", MediaType.APPLICATION_PDF);
		CONTENT_TYPE_MAP.put("jpg", MediaType.IMAGE_JPEG);
		CONTENT_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG);
		CONTENT_TYPE_MAP.put("png", MediaType.IMAGE_PNG);
		CONTENT_TYPE_MAP.put("csv", MediaType.TEXT_PLAIN);
		CONTENT_TYPE_MAP.put("xls", MediaType.valueOf("application/vnd.ms-excel"));
		// Add more content types as needed
	}

	@Override
	public ResponseEntity<Resource> openFileInNewTab(String fileName) throws IOException {
		Resource resource = new ClassPathResource("static/notics/" + fileName);
		//Resource resource = new ClassPathResource("notics/" + fileName);

		String fileExtension = getFileExtension(fileName);
		MediaType contentType = CONTENT_TYPE_MAP.getOrDefault(fileExtension.toLowerCase(),
				MediaType.APPLICATION_OCTET_STREAM);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType.toString());

		return ResponseEntity.ok().headers(headers).body(resource);
	}

	private String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
			return fileName.substring(lastDotIndex + 1);
		}
		return "";
	}

	@Override
	public void deleteNotice(long id) {
		noticeRepository.deleteById(id);
		
	}

	@Override
	public void updateNotice(NoticeEntity notice, MultipartFile file) {
	    try {
	        if (file != null && !file.isEmpty()) {
	            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
	            Path uploadPath = Paths.get(UPLOAD_DIR2);

	            // If the directory doesn't exist, create it
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            // Delete the old file if it exists
	            String oldFileName = notice.getFileName();
	            if (oldFileName != null && !oldFileName.isEmpty()) {
	                Path oldFilePath = uploadPath.resolve(oldFileName);
	                if (Files.exists(oldFilePath)) {
	                    Files.delete(oldFilePath);
	                    System.out.println("Old notice file deleted successfully: " + oldFileName);
	                }
	            }

	            // Save the new file to the server
	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(inputStream, uploadPath.resolve(originalFileName), StandardCopyOption.REPLACE_EXISTING);
	            }

	            System.out.println("Notice file updated successfully: " + originalFileName);
	            notice.setFileName(originalFileName);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    noticeRepository.save(notice);
	}



}
