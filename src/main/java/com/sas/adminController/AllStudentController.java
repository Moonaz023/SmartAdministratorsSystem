package com.sas.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.StudentEntity;
import com.sas.service.AllStudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AllStudentController {
	
	@Autowired
    private AllStudentService allStudentService;

	@GetMapping("/allStudent")
	public String index() {
		return "allStudent";
	}
	@PostMapping("/insertStudent")
	@ResponseBody
	public String insert(@ModelAttribute StudentEntity student,HttpSession session )
	{
		System.out.println(student);
		allStudentService.insertStudent(student);
		session.setAttribute("message", "yessssssss from here");
		return "save";
	}
	@GetMapping("/allfetchStudent")
	@ResponseBody
	public List<StudentEntity> getAllproduct(Model m, HttpSession session) {

	    List<StudentEntity> listOfstudent = allStudentService.fetchStudent();

	    return listOfstudent;
	}
	@PostMapping("/editStudent")
    @ResponseBody
    public String updateStudent( @ModelAttribute StudentEntity updatedStudent,HttpSession session ) {
        
		allStudentService.updateStudent(updatedStudent);

        return "Student record updated successfully";
    }
	@DeleteMapping("/deleteStudent")
	@ResponseBody
	public String deleteStudent(@RequestParam("id") String id) {
		allStudentService.deleteStudent(id);
	    return "Record deleted successfully";
	}
	@PostMapping("/insert_CSV_data")
    @ResponseBody
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
        try {
        	allStudentService.insertDataFromCSV(file);
            return "save"; // or return a success response
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // or return an error response
        }
    }
}
