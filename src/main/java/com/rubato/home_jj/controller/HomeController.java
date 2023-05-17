package com.rubato.home_jj.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubato.home_jj.dao.IDao;

@Controller
public class HomeController {
	
	@Autowired
	SqlSession sqlsession;
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	@RequestMapping(value = "/board_view")
	public String view() {
		return "board_view";
	}
	@RequestMapping(value = "/board_write")
	public String write() {
		return "board_write";
	}
	@RequestMapping(value = "/board_list")
	public String list() {
		return "board_list";
	}
	@RequestMapping(value = "/board_writeOk")
	public String writeOk(HttpServletRequest request) {
		
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		dao.boardWriteDao(bname, btitle, bcontent, "정회원", 0);
		
		return "redirect:board_list";
	}
	
	
}
