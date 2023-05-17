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
		
		model.addAttribute("replyList", dao.replyListDao(bnum));
		
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
	@RequestMapping(value = "/search_list")
	public String seach_list(HttpServletRequest request, Model model) {
		String searchOption	 = request.getParameter("searchOption");
		String keyWord = request.getParameter("keyWord");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		if(searchOption.equals("title")) {
			model.addAttribute("dtos", dao.boardSearchTitleDao(keyWord));
			model.addAttribute("totalCount", dao.boardSearchTitleDao(keyWord).size());
		}else if(searchOption.equals("content")){
			model.addAttribute("dtos", dao.boardSearchContentDao(keyWord));
			model.addAttribute("totalCount", dao.boardSearchContentDao(keyWord).size());
		}else {
			model.addAttribute("dtos", dao.boardSearchWriterDao(keyWord));
			model.addAttribute("totalCount", dao.boardSearchWriterDao(keyWord).size());
		}
		
		return "board_list";
	}
	@RequestMapping(value = "/reply_write")
	public String reply_write(HttpServletRequest request, Model model) {
			
		String rcontent = request.getParameter("rcontent");
		String bnum = request.getParameter("rorinum");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		
		
		dao.replyWriteDao(rcontent, bnum);
		
		dao.replyCountDao(bnum);
		
		model.addAttribute("boardDto", dao.boardContentViewDao(bnum));
		model.addAttribute("replyList", dao.replyListDao(bnum));
	
		
		return "board_view";
	}
	@RequestMapping(value = "/boradDelete")
	public String boradDelete(HttpServletRequest request) {
		
		String bnum = request.getParameter("bnum");
		IDao dao = sqlsession.getMapper(IDao.class);
		dao.boardDeleteDao(bnum);
		dao.replyDelete2(bnum);
		
		return "redirect:board_list";
	}
	
	@RequestMapping(value = "/replyDelete")
	public String replyDelete(HttpServletRequest request, Model model) {
		String rnum = request.getParameter("rnum");
		String rorinum = request.getParameter("rorinum");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		dao.replyCountDownDao(rorinum);
		dao.replyDelete(rnum);
		model.addAttribute("boardDto", dao.boardContentViewDao(rorinum));
		model.addAttribute("replyList", dao.replyListDao(rorinum));
		
		return "board_view";
	}

}
