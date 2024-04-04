package com.sas.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sas.entity.StudentDetailsEntity;
import com.sas.entity.StudentEntity;
import com.sas.entity.User;
import com.sas.entity.ChangePasswordRequest;
import com.sas.repository.AllStudentRepository;
import com.sas.repository.StudentDetailsRepository;
import com.sas.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AllStudentServiceImp implements AllStudentService {

	@Autowired
	private AllStudentRepository allStudentRepository;
	@Autowired
	private StudentDetailsRepository studentDetailsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void insertStudent(StudentEntity student) {
		StudentDetailsEntity studentDetailsEntity=new StudentDetailsEntity();
		studentDetailsEntity.setStudent(student);
		student.setDetails(studentDetailsEntity);
		allStudentRepository.save(student);
	}
	@Override
	@Transactional
	public List<StudentEntity> fetchStudent() {
		
		return allStudentRepository.findAll();
	}
	@Override
	public void updateStudent(StudentEntity updatedStudent) {
		allStudentRepository.save(updatedStudent);
		
	}
	@Override
	public void deleteStudent(String id) {
		allStudentRepository.deleteById(id);
		
	}
	@Override
	public void insertDataFromCSV(MultipartFile file) throws IOException, CsvValidationException, NumberFormatException {
	    CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

	    String[] nextLine;

	    while ((nextLine = reader.readNext()) != null) {
	        StudentEntity student = new StudentEntity();
	        student.setRoll(nextLine[0]);
	        student.setSession(nextLine[1]);
	        student.setRegistration(nextLine[2]);
	        student.setName(nextLine[3]);
	        

	        StudentDetailsEntity studentDetailsEntity=new StudentDetailsEntity();
			studentDetailsEntity.setStudent(student);
			student.setDetails(studentDetailsEntity);
	        

	        allStudentRepository.save(student);
	    }

	    reader.close();
	}
	@Override
	public StudentEntity fetchStudentByUserName(String userName) {
		
		return allStudentRepository.findByRoll(userName);
	}
	@Override
	public void updateStudentDetails(StudentDetailsEntity updatedStudentDetails) {
		studentDetailsRepository.save(updatedStudentDetails);
		
	}
	@Override
	public String ChangePasswordRequest(ChangePasswordRequest updatedPassword) {
	    int userId = updatedPassword.getId();
	    java.util.Optional<User> optionalUser = userRepository.findById(userId);

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        String encodedNewPassword = passwordEncoder.encode(updatedPassword.getNewPassword());

	        
	        if (passwordEncoder.matches(updatedPassword.getCurrentPassword(), user.getPassword())) {
	            
	            user.setPassword(encodedNewPassword);
	            userRepository.save(user);
	            return "success";
	        } else {
	            
	            System.out.println("Wrong password");
	            return "failed";
	        }
	    } else {
	        System.out.println("User not found with ID: " + userId);
	        return "failed";
	    }
	}
	@Override
	public StudentEntity checkDetails(String roll) {
		return allStudentRepository.findByRoll(roll);
	}

	
		
	

}
