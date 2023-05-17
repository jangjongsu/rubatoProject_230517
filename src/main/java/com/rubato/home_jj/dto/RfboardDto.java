package com.rubato.home_jj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RfboardDto {
	
	private int bnum;
	private String bname;
	private String btitle;
	private String bcontent;
	private int bhit;
	private String buserid;
	private int breplycount;
	private String bdate;
	private int bfilecount;

}
