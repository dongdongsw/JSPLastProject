package com.sist.model;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BoardReplyModel {

	@RequestMapping("main/boardReply_insert")
	public String  boardreply_insert(HttpServletRequest request, HttpServletResponse response) {
		
		return "";
	}
	
	@RequestMapping("main/boardReply_delete")
	public String  boardreply_delete(HttpServletRequest request, HttpServletResponse response) {
		
		return "";
	}
}
