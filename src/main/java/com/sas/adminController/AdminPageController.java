package com.sas.adminController;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sas.entity.Type;
import com.sas.entity.User;
import com.sas.repository.TypeRepository;
import com.sas.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TypeRepository typeRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	@GetMapping("/home")
	public String home()
	{
		return "adminPanel";
	}

	@GetMapping("/profile")
	public String profile() {
		return "admin_profile";
	}
	@GetMapping("/CreatType")
	@ResponseBody
	public void creatType() {
		Type t = new Type();
		t.setName("electric");
		typeRepo.save(t);
		Type t2 = new Type();
		t2.setName("furniture");
		typeRepo.save(t2);
	}
	
}
