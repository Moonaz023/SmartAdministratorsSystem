package com.sas.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sas.entity.User;
import com.sas.entity.UserDOT;
import com.sas.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {

		String password=passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		
		User newuser = userRepo.save(user);

		return newuser;
	}

	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}

	@Override
	public void saveStudent(UserDOT userDOT) {
		String password=passwordEncoder.encode(userDOT.getPassword());
		String email=userDOT.getEmail();
		String name=userDOT.getName();
		
		User user=new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setRole("ROLE_USER");
		
		userRepo.save(user);

		
		
	}

	@Override
	public void insertuserDataFromCSV(MultipartFile file) throws IOException, CsvValidationException, NumberFormatException {
	    CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
	    String[] nextLine;
	    boolean isFirstRow = true; // Flag to identify the first row

	    while ((nextLine = reader.readNext()) != null) {
	        if (isFirstRow) {
	            isFirstRow = false; // Skip the first row
	            continue;
	        }

	        User user = new User();
	        user.setName(nextLine[0]);
	        user.setEmail(nextLine[1]);
	        String password = passwordEncoder.encode(nextLine[2]);
	        user.setPassword(password);
	        user.setRole("ROLE_USER");
	        userRepo.save(user);
	    }
	    reader.close(); // Close the CSV reader
	}

	
}
