package com.sas.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.sas.entity.StudentEntity;
import com.sas.repository.AllStudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class GenerateStudentDocServiceImp implements GenerateStudentDocService {

	@Autowired
	private AllStudentRepository allStudentRepository;
	@Override
	public void generateTestimonial(String roll, String passingYear, String examHeldIn, Double cGPA, HttpServletResponse response) throws IOException, DocumentException  {
		StudentEntity student = allStudentRepository.findById(roll).orElse(null);
   	// System.out.println("Hurryyyyyyyyyyyyyyyyyyyyyyyyyyyyy successfully."+student.getName()+student.getDetails().getFatherName());
   	 
   	 
   	 response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"Testimonial.pdf\"");

	    // Step 1: Create a Font object for the Dancing Script font
	    FontFactory.register(new ClassPathResource("fonts/DancingScript-VariableFont_wght.ttf").getURL().toString(), "Dancing Script");
	    Font dancingScriptFont = FontFactory.getFont("Dancing Script", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	    
	    Document document = new Document();
	    PdfWriter.getInstance(document, response.getOutputStream());

	    document.open();

	    // Step 2: Apply the Dancing Script font to the document
	    Font font = dancingScriptFont;
	    Font dbFont = FontFactory.getFont(FontFactory.HELVETICA);
	    
	    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.UNDERLINE);
	    Paragraph paragraph = new Paragraph("TESTIMONIAL", titleFont);
	    paragraph.setAlignment(Element.ALIGN_CENTER);
	   // document.add(paragraph);

	   // for (Table1 account : accounts) {
	        // Step 3: Apply the Dancing Script font to the content
	        Paragraph phrase = new Paragraph();
	        phrase.setFont(font);
	        Paragraph dbtxt = new Paragraph();
	        dbtxt.setFont(dbFont);
	        Paragraph center = new Paragraph();
	        center.setFont(font);

	       
	        document.add(paragraph);
	        center.add(Chunk.NEWLINE);
	        center.add(Chunk.NEWLINE);
	        center.setAlignment(Element.ALIGN_CENTER);
	        center.add(new Chunk("I have immense pleasure to certify that" ));
	        center.add(Chunk.NEWLINE);
	        document.add(center);
	        int numOfSpaces = 60; // Specify the number of spaces you want to add
	        String spaces = new String(new char[numOfSpaces]).replace('\0', ' ');
	        phrase.setFont(dbFont);
	        phrase.add(new Chunk(spaces + student.getName()));
	        phrase.add(Chunk.NEWLINE);
	        phrase.setFont(font);
	        phrase.add(new Chunk("Mr/Miss/Mrs...................................................................................................................................................................................." ));
	        numOfSpaces = 30; // Specify the number of spaces you want to add
	         spaces = new String(new char[numOfSpaces]).replace('\0', ' ');
	         phrase.setFont(dbFont);
	         phrase.add(new Chunk(spaces + student.getDetails().getFatherName()));
	         String father = student.getDetails().getFatherName();
	         int len = father.length();
	         int gap;
	         gap=65-(2*len);
	         spaces = new String(new char[gap]).replace('\0', ' ');
	         phrase.add(new Chunk(spaces + student.getDetails().getMotherName()));
	         phrase.add(Chunk.NEWLINE);
	         phrase.setFont(font);
	         phrase.add(new Chunk("Son/Daughter of.............................................................................and............................................................................................" ));
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(new Chunk("was a reguler student of 4-Year Bacheler of Science (B.Sc) with Honors program in the Depsrtment of Computer Science " ));
	         phrase.add(Chunk.NEWLINE);
	         spaces = new String(new char[90]).replace('\0', ' ');
	         phrase.setFont(dbFont);
	         phrase.add(new Chunk(spaces + student.getSession()));
	         spaces = new String(new char[30]).replace('\0', ' ');
	         phrase.add(new Chunk(spaces + passingYear));
	         phrase.add(Chunk.NEWLINE);
	         phrase.setFont(font);
	         phrase.add(new Chunk("and Engineering,University of Barisal.His/her academic session was.............................,passing year......................................." ));
	         phrase.add(Chunk.NEWLINE);
	         spaces = new String(new char[20]).replace('\0', ' ');
	         phrase.setFont(dbFont);
	         phrase.add(new Chunk(spaces + examHeldIn));
	         if(examHeldIn.length()<12)
	        	 spaces = new String(new char[65]).replace('\0', ' ');
	         else
	         spaces = new String(new char[50]).replace('\0', ' ');
	         phrase.add(new Chunk(spaces + cGPA));
	         phrase.add(Chunk.NEWLINE);
	         phrase.setFont(font);
	         phrase.add(new Chunk("Exam held in............................................and he/she obtained a CGPA of......................... on scale of 4.00.His/her Conduct/" ));
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(new Chunk("Behavior as a student during the period of study in this department was satisfactory and he bear a good moral character." ));
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);	
	         phrase.add(new Chunk("Is the best of my knowledge, he did not take part in any activity subversive to the state or discipline." ));
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(new Chunk("I wish him/her all the success in life." ));
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.add(Chunk.NEWLINE);
	         phrase.setFont(dbFont);
	         spaces = new String(new char[115]).replace('\0', ' ');
	         phrase.add(new Chunk(spaces +"Chairman"));
	         phrase.add(Chunk.NEWLINE);

	         LocalDate currentDate = LocalDate.now();
	         String dateString = "Date: " + currentDate.toString();
	         
	         //String dateString = "Date: " + currentDate.toString();
	         
	         spaces = new String(new char[58]).replace('\0', ' ');
	         phrase.add(new Chunk(dateString+spaces +"Dept. of Computer Science and Engineering"));
	         phrase.add(Chunk.NEWLINE);
	         spaces = new String(new char[108]).replace('\0', ' ');
	         phrase.add(new Chunk(spaces +"University of Barisal"));
	         
	         
	         document.add(phrase);			
	         document.newPage(); // Add a new page for each record

	    

	    document.close();
	    
	    System.out.println("PDF generation completed successfully."+roll); // Debug statement
		
	}

}
