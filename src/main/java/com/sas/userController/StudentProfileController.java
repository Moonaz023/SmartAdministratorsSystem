package com.sas.userController;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.sas.entity.StudentDetailsEntity;
import com.sas.entity.StudentEntity;
import com.sas.entity.User;
import com.sas.entity.ChangePasswordRequest;
import com.sas.repository.UserRepository;
import com.sas.service.AllStudentService;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
@Controller
public class StudentProfileController {

	@Autowired
    private AllStudentService allStudentService;

	@Autowired
	private UserRepository userRepo;
	private static final String UPLOAD_DIR = "src\\main\\resources\\static\\uploads";
	
	//private static final String UPLOAD_DIR = "/uploads";
	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}
//	@GetMapping("/studentProfile")
//	public String index() {
//		return "student-profile";
//	}
	@GetMapping("/user/profile")
	public String studentProfile() {
		return "student-profile";
	}
	@GetMapping("/user/changePassword")
	public String changePassword() {
		return "changePassword";
	}
	
	@GetMapping("/fetchStudentbyUserName/{userName}")
	@ResponseBody
    public StudentEntity fetchStudentByUserName(@PathVariable String userName) {
        
		StudentEntity student = allStudentService.fetchStudentByUserName(userName);
		return student;
    }
	@PostMapping("/editStudentProfile")
    @ResponseBody
    public String updateStudent( @ModelAttribute StudentDetailsEntity updatedStudentDetails,HttpSession session ) {
        
		allStudentService.updateStudentDetails(updatedStudentDetails);

        return "Student record updated successfully";
    }
	@PostMapping("/user/changePassword")
    @ResponseBody
    public String ChangePasswordRequest(ChangePasswordRequest updatedPassword) {
		//System.out.println("Controller :User not found with ID: " + updatedPassword.getNewPassword());
		return allStudentService.ChangePasswordRequest(updatedPassword);

      
    }
	@PostMapping("/uploadImage")
	 @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("profileImage") MultipartFile file, @RequestParam("fileName") String fileName) {
        try {
           
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            
            //String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            //String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String finalFileName = fileName + ".jpg";

            
            Path uploadPath = Paths.get(UPLOAD_DIR);

            // If the directory doesn't exist, create it
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the server
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(finalFileName), StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Image uploaded successfully: " + finalFileName);

            return ResponseEntity.ok("Image uploaded successfully: " + finalFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading image");
        }
    }

	@GetMapping("/images/{imageName}")
	public ResponseEntity<Resource> serveImage(@PathVariable String imageName) throws IOException {
		
		System.out.println("img url hit=================>>>");	    // Find the image file with any extension by checking each supported extension
	    List<String> supportedExtensions = Arrays.asList("png", "jpg", "jpeg", "gif"); 

	    Path imagePath = null;

	    for (String extension : supportedExtensions) {
	        Path potentialImagePath = Paths.get(UPLOAD_DIR).resolve(imageName + "." + extension);
	        System.out.println("img url hit=================>>>"+potentialImagePath);
	        if (Files.exists(potentialImagePath)) {
	            imagePath = potentialImagePath;
	            break;
	        }
	    }

	    if (imagePath != null) {
	    	System.out.println("img url hit=================>>>Path not null");
	        Resource imageResource = new UrlResource(imagePath.toUri());
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_TYPE, "image/*")
	                .body(imageResource);
	    } else {
	    	System.out.println("img url hit=================>>>Path is null");
	        // If the image file is not found, return a default image
	        Path defaultImagePath = Paths.get(UPLOAD_DIR).resolve("default.jpg");
	        Resource defaultImageResource = new UrlResource(defaultImagePath.toUri());

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_TYPE, "image/*")
	                .body(defaultImageResource);
	    }
	}


}
