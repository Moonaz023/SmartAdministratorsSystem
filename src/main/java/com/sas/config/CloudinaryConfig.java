package com.sas.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	@Bean
	public Cloudinary configCloudinary() {
		Map map=new HashMap();
		map.put("cloud_name","dy6twgshm");
		map.put("api_key","215183483199872");
		map.put("api_secret","vIy9TOZlhItbHuY7u01QmGhWzRo");
		map.put("secure",true);
		map.put("cdn_subdomain", true);
		
		
		return new Cloudinary(map);
		
	}

}
