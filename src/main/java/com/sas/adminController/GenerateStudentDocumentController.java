package com.sas.adminController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;
import com.sas.service.GenerateStudentDocService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GenerateStudentDocumentController {

	@Autowired
	GenerateStudentDocService generateStudentDocService;
	@GetMapping("/admin/generateTestimonial")
	@ResponseBody
    public void downloadSinglePDF(@RequestParam("roll") String roll,@RequestParam("passingYear") String passingYear,@RequestParam("ExamHeldIn") String ExamHeldIn,@RequestParam("CGPA") Double CGPA, HttpServletResponse response) throws IOException, DocumentException  {
        
		// System.out.println("Controller:PDF generation completed successfully.");
		generateStudentDocService.generateTestimonial(roll,passingYear,ExamHeldIn,CGPA, response);
    	 
	}
	
	

}
