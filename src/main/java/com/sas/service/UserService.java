package com.sas.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvValidationException;
import com.sas.entity.User;
import com.sas.entity.UserDOT;

public interface UserService {

	public User saveUser(User user);

	public void removeSessionMessage();

	void saveStudent(UserDOT user);

	public void insertuserDataFromCSV(MultipartFile file) throws IOException, CsvValidationException, NumberFormatException;

}
