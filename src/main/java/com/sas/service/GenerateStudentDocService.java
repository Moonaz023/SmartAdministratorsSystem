package com.sas.service;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

public interface GenerateStudentDocService {

	void generateTestimonial(String roll, String passingYear, String examHeldIn, Double cGPA, HttpServletResponse response) throws IOException, DocumentException  ;

	

}
