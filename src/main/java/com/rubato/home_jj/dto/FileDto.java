package com.rubato.home_jj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
	
	private int filenum;
	private int forinum;
	private String fileoriname;
	private String filename;
	private String fileextension;
	private	String fileurl;

}
