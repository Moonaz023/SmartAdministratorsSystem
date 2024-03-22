package com.sas.adminController;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.NoticeEntity;
import com.sas.service.NoticeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@PostMapping("/admin/insertnotice")
	@ResponseBody
	public String insert(@ModelAttribute NoticeEntity notice,HttpSession session,@RequestParam("noticDoc") MultipartFile file )
	{
		
		  System.out.println("controller Checked");
	    noticeService.insertNotice(notice,file);
		return "Notice saved";
	}
}
