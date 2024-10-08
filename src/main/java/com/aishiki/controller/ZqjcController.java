package com.aishiki.controller;

import javax.servlet.http.HttpSession;

import com.aishiki.model.User;
import com.aishiki.service.ZqjcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aishiki.model.Student;
import com.aishiki.model.Zqjc;
import com.aishiki.service.StudentService;

@Controller
@RequestMapping("/zqjcController")
public class ZqjcController {
	
	@Autowired
	private ZqjcService zqjcService;
	@Autowired
	private StudentService studentService;
	
	@ResponseBody
	@RequestMapping(value="/insertSelective",method=RequestMethod.POST)
	public boolean insertSelective(Zqjc zqjc) {
		if(zqjc.getZqjcId()==null) {
			return zqjcService.insertSelective(zqjc);
		}else {
			return zqjcService.updateByPrimaryKeySelective(zqjc);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getzqjcByStudentId")
	public Zqjc getzqjcByStudentId(@RequestParam(value="studentId",required=false)String studentId,
			HttpSession session) {
		if(studentId!=null&&!studentId.isEmpty()) {
			Zqjc zqjc = zqjcService.getzqjcByStudentId(studentId);
			if(zqjc!=null) {
				return zqjc;
			}
		}else {
			User user = (User) session.getAttribute("user");
			if(user!=null) {
				Student student = studentService.getStudentByUserId(user.getUserId());
				if(student!=null) {
					Zqjc zqjc = zqjcService.getzqjcByStudentId(student.getStudentId());
					if(zqjc!=null) {
						return zqjc;
					}
				}
			}
		}
		return new Zqjc();
	}
	
	@RequestMapping("/gotoTzqjc")
	public String gotoTzqjc(@RequestParam(required=true)String studentId) {
		return "redirect:http://localhost:8989/graduation/teacher/tzqjc.html?studentId="+studentId;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateByPrimaryKeySelective",method=RequestMethod.PUT)
	public boolean updateByPrimaryKeySelective(Zqjc zqjc) {
		return zqjcService.updateByPrimaryKeySelective(zqjc);
	}

}
