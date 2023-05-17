package com.rubato.home_jj.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubato.home_jj.dao.IDao;
import com.rubato.home_jj.dto.RfboardDto;

@Controller
public class HomeController {
	
	@Autowired
	SqlSession sqlsession;
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	@RequestMapping(value = "/board_view")
	public String view(HttpServletRequest request, Model model) {
		
		String bnum = request.getParameter("bnum");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		dao.boardHitDao(bnum);
		
		model.addAttribute("boardDto", dao.boardContentViewDao(bnum));
		
		return "board_view";
	}
	@RequestMapping(value = "/board_write")
	public String write() {
		return "board_write";
	}
	@RequestMapping(value = "/board_list")
	public String list(Model model) {
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		List<RfboardDto> dtos =  dao.boardListDao();
		
		model.addAttribute("dtos", dtos);
		model.addAttribute("totalCount", dao.boardTtalCountDao());
		
		
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
