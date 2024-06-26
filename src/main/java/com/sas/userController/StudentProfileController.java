package com.sas.userController;
//	private static final String UPLOAD_DIR = "src\\main\\resources\\static\\uploads";
//	private static final String UPLOAD_DIR = "uploads";

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
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
import com.cloudinary.Cloudinary;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sas.entity.ChangePasswordRequest;
import com.sas.repository.UserRepository;
import com.sas.service.AllStudentService;
import com.sas.service.CloudinaryFileService;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
@Controller
public class StudentProfileController {

    @Autowired
    private AllStudentService allStudentService;

    @Autowired
    private UserRepository userRepo;

    public final String UPLOAD_DIR2 = new ClassPathResource("static/uploads").getPath();

    public StudentProfileController() throws IOException {
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
    }

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
    public String updateStudent(@ModelAttribute StudentDetailsEntity updatedStudentDetails, HttpSession session) {
        allStudentService.updateStudentDetails(updatedStudentDetails);
        return "Student record updated successfully";
    }

    @PostMapping("/user/changePassword")
    @ResponseBody
    public String ChangePasswordRequest(@ModelAttribute ChangePasswordRequest updatedPassword) {
        return allStudentService.ChangePasswordRequest(updatedPassword);
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("profileImage") MultipartFile file, @RequestParam("fileName") String fileName) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            String finalFileName = fileName + ".jpg";

            Path uploadPath = Paths.get(UPLOAD_DIR2);

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
        List<String> supportedExtensions = Arrays.asList("png", "jpg", "jpeg", "gif");

        Path imagePath = null;

        for (String extension : supportedExtensions) {
            Path potentialImagePath = Paths.get(UPLOAD_DIR2).resolve(imageName + "." + extension);
            if (Files.exists(potentialImagePath)) {
                imagePath = potentialImagePath;
                break;
            }
        }

        if (imagePath != null) {
            Resource imageResource = new UrlResource(imagePath.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/*")
                    .body(imageResource);
        } else {
            // If the image file is not found, return a default image
            Path defaultImagePath = Paths.get(UPLOAD_DIR2).resolve("default.jpg");
            Resource defaultImageResource = new UrlResource(defaultImagePath.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/*")
                    .body(defaultImageResource);
        }
    }
 // @GetMapping("/images/{imageName}")
    // public ResponseEntity<String> checkLinkValidity(@PathVariable String imageName)
    // throws IOException {
    // String
    // link="https://res.cloudinary.com/dy6twgshm/image/upload/"+imageName;
    // try {
    // URL url = new URL(link);
    // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    // connection.setRequestMethod("HEAD");
    // int responseCode = connection.getResponseCode();
    //
    // if (responseCode == HttpURLConnection.HTTP_OK) {
    // // Link is valid
    // return ResponseEntity.ok("https://res.cloudinary.com/dy6twgshm/image/upload/v1712750310/"+imageName);
    // } else {
    // // Link is not valid
    // return
    // ResponseEntity.ok("https://res.cloudinary.com/dy6twgshm/image/upload/v1712750310/default");
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // // Error occurred, return default image path
    // return
    // ResponseEntity.ok("https://res.cloudinary.com/dy6twgshm/image/upload/v1712750310/default");
    // }
    // }



	
//	@Autowired
//    private CloudinaryFileService cloudinary;
//
//    @PostMapping("/imgup")
//    public ResponseEntity<Map> imguploadcloud(@RequestParam("profileImage") MultipartFile file, @RequestParam("fileName") String fileName) {
//        System.out.println("got it controller");
//        Map data = this.cloudinary.Upload(file,fileName);
//        String secureUrl = (String)data.get("secure_url");
//        System.out.println("Secure URL: " + secureUrl);
//        return ResponseEntity.ok(data);
//    }
}
