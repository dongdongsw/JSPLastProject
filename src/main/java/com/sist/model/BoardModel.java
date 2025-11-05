package com.sist.model;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BoardModel {
	@RequestMapping("board/list.do")
	public String board_list(HttpServletRequest request, HttpServletResponse response) {
		
		// 사용자가 페이지 요청
		String page = request.getParameter("page");
		if(page==null) page="1";
		int curpage = Integer.parseInt(page);
		int rowSize = 12;
		int start = (curpage-1) * rowSize;
		List<BoardVO> list = BoardDAO.boardListData(start); 
		int count = BoardDAO.boardTotalPage();
		int totalpage = (int)(Math.ceil(count/10.0));
		
		 
		//int endPage = rowSize*curpage;
		
		/*
		 * // 블록별 페이지 처리 final int BLOCK = 10; int
		 * startPage=((curpage-1)/BLOCK*BLOCK)+1; // curpage => 1~10 : 1, 11 ~ 20 : 11
		 * int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		 * 
		 * if(endPage > totalpage) endPage = totalpage;
		 */
		count = count -(((curpage-1) * rowSize));
		request.setAttribute("count",count);
		request.setAttribute("today",
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		request.setAttribute("curpage",curpage);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("list",list);
		// request.setAttribute("startPage",startPage);
		// request.setAttribute("endPage",endPage);
		
		request.setAttribute("main_jsp", "../board/list.jsp");
		return "../main/main.jsp";
	}
	
	@RequestMapping("board/insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../board/insert.jsp");
		return "../main/main.jsp";
	}
	
	@RequestMapping("board/insert_ok.do")
	public String board_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		
		// https://www.google.com/search?q=%EC%9E%90%EB%B0%94&oq=%EC%9E%90%EB%B0%94&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTINCAEQABiDARixAxiABDIKCAIQABixAxiABDINCAMQLhjHARjRAxiABDIGCAQQABgDMg0IBRAAGIMBGLEDGIAEMgoIBhAAGLEDGIAEMgYIBxBFGEHSAQc0OTdqMGo3qAIIsAIB&sourceid=chrome&ie=UTF-8
										 // ================= 이게 자바 라는 단어임
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) {
				ex.printStackTrace();
		}
		
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");		
		
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		BoardDAO.boardInsert(vo);
		return "redirect:../board/list.do";
	}
	
	@RequestMapping("board/detail.do")
	public String board_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		String page = request.getParameter("page");
		
		BoardVO vo = BoardDAO.boardDetailData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("page", page);
		request.setAttribute("main_jsp", "../board/detail.jsp");
		return "../main/main.jsp";
	}
	
	
	/*
	 * Spring
	 * ------
	 * | String : 화면 변경
	 * 			 forward / redirect
	 *   void : JSON
	 * 
	 */
	
	
	@RequestMapping("board/delete.do")
	   public void board_delete(HttpServletRequest request,
			   HttpServletResponse response)
	   {
		   //1. 사용자 전송값 data:{"no":no,"pwd":pwd}
		   //2. => ?no=1&pwd=1234
		   //vue => axios => params:{"no":no,"pwd":pwd}
		   String no=request.getParameter("no");
		   String pwd=request.getParameter("pwd");
		   // => DAO로 전송 => 결과값 
		   String res=
			BoardDAO.boardDelete(Integer.parseInt(no), pwd);
		   try
		   {
			   response.setContentType("text/plain;charset=UTF-8");
			   PrintWriter out=response.getWriter();
			   out.write(res);
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }
		   
	   }
	
	@RequestMapping("board/update.do")
	public String board_update(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		String page=request.getParameter("page");
		BoardVO vo = BoardDAO.boardupdateData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("page", page);
		request.setAttribute("main_jsp", "../board/update.jsp");
		return "../main/main.jsp";
	}
	
	@RequestMapping("board/pwdcheck.do")
	public void board_pwdcheck(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		String pwd=request.getParameter("pwd");
		String res = BoardDAO.boardPwdCheck(Integer.parseInt(no), pwd);
		
		
		try {
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(res); // VO => {}, List => [] => JSON
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	@RequestMapping("board/update_ok.do")
	public void board_update_ok(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) {
				ex.printStackTrace();
		}
		
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");	
		String no = request.getParameter("no");
		
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		vo.setNo(Integer.parseInt(no));
		
		String res = BoardDAO.boardUpdate(vo);
		
		try {
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(res); // VO => {}, List => [] => JSON
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
}
