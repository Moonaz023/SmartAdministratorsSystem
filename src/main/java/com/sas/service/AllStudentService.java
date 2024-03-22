package com.sas.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvValidationException;
import com.sas.entity.ChangePasswordRequest;
import com.sas.entity.StudentDetailsEntity;
import com.sas.entity.StudentEntity;

public interface AllStudentService {

	void insertStudent(StudentEntity student);

	List<StudentEntity> fetchStudent();

	void updateStudent(StudentEntity updatedStudent);

	void deleteStudent(String id);

	public void insertDataFromCSV(MultipartFile file) throws IOException, CsvValidationException, NumberFormatException ;

	StudentEntity fetchStudentByUserName(String userName);

	void updateStudentDetails(StudentDetailsEntity updatedStudentDetails);

	String ChangePasswordRequest(ChangePasswordRequest updatedPassword);

}
