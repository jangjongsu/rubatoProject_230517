package com.rubato.home_jj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
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
	
}
