package com.rubato.home_jj.dao;

import java.util.List;

import com.rubato.home_jj.dto.RfboardDto;
import com.rubato.home_jj.dto.RreplyDto;

public interface IDao {
	
	//게시판 기본 기능
	public int boardWriteDao (String bname, String btitle, String bcontent, String buserid, int filecount);
	public List<RfboardDto> boardListDao();
	public int boardTtalCountDao();
	public RfboardDto boardContentViewDao(String bnum);
	public void boardHitDao(String bnum);
	public void boardDeleteDao(String bnum);
	
	
	//게시판 검색기능
	public List<RfboardDto> boardSearchContentDao(String keyword);
	public List<RfboardDto> boardSearchTitleDao(String keyword);
	public List<RfboardDto> boardSearchWriterDao(String keyword);
	
	//댓글
	public int replyWriteDao(String rcontent, String rorinum);
	public void replyCountDao(String rorinum);
	public List<RreplyDto> replyListDao(String rorinum);
	public void replyDelete(String rnum);
	public void replyDelete2(String rorinum);
	public void replyCountDownDao(String rorinum);
}
