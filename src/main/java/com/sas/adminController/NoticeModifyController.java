package com.sas.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.NoticeEntity;
import com.sas.service.NoticeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeModifyController {
	@Autowired
	private NoticeService noticeService;
	@GetMapping("/admin/fetchNotice")
	@ResponseBody
	public List<NoticeEntity> fetchNotice(Model m, HttpSession session) {
		return noticeService.fetchNotice();
	   
	}
	@DeleteMapping("/admin/deleteNotice")
	@ResponseBody
	public String deleteStudent(@RequestParam("id") Long id) {
		noticeService.deleteNotice(id);
	    return "Record deleted successfully";
	}
	
	@PostMapping("/admin/updatenotice")
	@ResponseBody
	public String updateNotice(@ModelAttribute NoticeEntity notice,HttpSession session,@RequestParam("noticDoc") MultipartFile file )
	{
		noticeService.updateNotice(notice,file);
		  System.out.println("Notice update controller Checked");
	    //noticeService.insertNotice(notice,file);
		return "Notice updated";
	}
}
