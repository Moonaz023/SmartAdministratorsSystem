package com.sas.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.NoticeEntity;

public interface NoticeService {

	void insertNotice(NoticeEntity notice, MultipartFile file);

	List<NoticeEntity> fetchNotice();

	ResponseEntity<Resource> openFileInNewTab(String fileName) throws IOException;

	void deleteNotice(long id);

	void updateNotice(NoticeEntity notice, MultipartFile file);

}
