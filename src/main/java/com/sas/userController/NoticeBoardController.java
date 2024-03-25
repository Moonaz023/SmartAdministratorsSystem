package com.sas.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sas.entity.NoticeEntity;
import com.sas.entity.User;
import com.sas.repository.UserRepository;
import com.sas.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.security.Principal;
@Controller
public class NoticeBoardController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserRepository userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	@GetMapping("/user/notice")
	public String notice() {
		return "NoticeBoard";
	}


	@GetMapping("/user/fetchNotice")
	@ResponseBody
	public List<NoticeEntity> fetchNotice(Model m, HttpSession session) {
		return noticeService.fetchNotice();
	   
	}
	

    @GetMapping("/user/open/{fileName}") 
    public ResponseEntity<Resource> openFileInNewTab(@PathVariable String fileName) throws IOException {
    	return noticeService.openFileInNewTab(fileName);
       
    }
   
}
