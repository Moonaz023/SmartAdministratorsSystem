package com.sas.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryFileServiceImp implements CloudinaryFileService {

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public Map Upload(MultipartFile C_file, String fileName) {
		String FIXED_VERSION = "v1712750310";
		System.out.println("got it");
		try {
			Map uploadOptions = ObjectUtils.asMap("public_id", fileName,
	                "version", FIXED_VERSION, "overwrite", true);
			Map dataMap=cloudinary.uploader().upload(C_file.getBytes(), uploadOptions);
			return dataMap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
