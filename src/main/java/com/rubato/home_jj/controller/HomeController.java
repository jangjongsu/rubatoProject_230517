package com.rubato.home_jj.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
		
		model.addAttribute("fileDto", dao.getFileInfoDao(bnum));
		
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
	public String writeOk(HttpServletRequest request, @RequestPart MultipartFile files) throws IllegalStateException, IOException {
		
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		IDao dao = sqlsession.getMapper(IDao.class);
		
		if(files.isEmpty()) {//true 면 파일 첨부 x
			dao.boardWriteDao(bname, btitle, bcontent, "정회원", 0); // 파일첨부 없이 글만 입력
			
		}else{ // 파일이 첨부된 경우
			dao.boardWriteDao(bname, btitle, bcontent, "정회원", 1); //파일이 첨부된 상태로 글을 쓴경우
			 List<RfboardDto> boardList = dao.boardListDao();//모든 글 가져오기
			 RfboardDto boardDto = boardList.get(0); // 방금쓴글
			 int forinum = boardDto.getBnum(); //방금쓴글의 번호(파일이 첨부된 글의 번호)
			 
			 //파일첨부 관련 작업
			String fileoriname = files.getOriginalFilename();//첨부된 파일의 원래 이름
			String fileextension = FilenameUtils.getExtension(fileoriname).toLowerCase(); //파일의 확장자를 자져오기 . 후에 소문자로 기져오기
			File destinationFile;
			String destinationFileName; //실제 서버에 저장된 파일의 변경된 이름이 저장될 변수
			String fileurl = "D:/springboot_workspace/rubatoHomeProject_230517_jj/src/main/resources/static/uploadFiles/"; //실제첨부된 파일이 저장된 서버의 실제 폴더의 경로
			
			
			do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileextension; //알파벳대소문자+숫자로 이루어진 랜덤 32글자의 문자열 이름으로된 파일이름 생성 -> 서버에 이 이름으로 저장
			destinationFile = new File(fileurl + destinationFileName);
			}while(destinationFile.exists()); //같은 파일이름이 혹시 존재하는지 확인
			
			destinationFile.getParentFile().mkdir();  
			files.transferTo(destinationFile); // 업로드된 첨부된 파일이 지정한 폴더로 이동 완료!
			
			dao.fileIofoCreateDao(forinum, fileoriname, destinationFileName, fileextension, fileurl);
		}
		
		
		
		
		
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
