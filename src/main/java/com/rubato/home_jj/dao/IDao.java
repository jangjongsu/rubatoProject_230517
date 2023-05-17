package com.rubato.home_jj.dao;

import java.util.List;

import com.rubato.home_jj.dto.RfboardDto;

public interface IDao {
	
	public int boardWriteDao (String bname, String btitle, String bcontent, String buserid, int filecount);
	public List<RfboardDto> boardListDao();
	public int boardTtalCountDao();
	public RfboardDto boardContentViewDao(String bnum);
	public void boardHitDao(String bnum);

}
