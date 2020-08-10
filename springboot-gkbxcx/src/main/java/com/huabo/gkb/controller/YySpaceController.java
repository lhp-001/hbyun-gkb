package com.huabo.gkb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("yySpace")
public class YySpaceController {

	
	@RequestMapping("/yyInterfaceIn")
	public ModelAndView yyInterfaceIn(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/index");
		String code = request.getParameter("code");
		System.out.println(code);
		return mv;
	}
	
	@RequestMapping("/goTokongPage")
	public ModelAndView goTokongPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/kong");
		return mv;
	}
	
	@RequestMapping("/goToworkPage")
	public ModelAndView goToworkPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/work");
		return mv;
	}
	
	@RequestMapping("/goTomyPage")
	public ModelAndView goTomyPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/my");
		return mv;
	}
	
	@RequestMapping("/goToindexPage")
	public ModelAndView goToindexPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/index");
		return mv;
	}
	
	@RequestMapping("/goToPlanListPage")
	public ModelAndView goToPlanListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("yyspace/planList");
		return mv;
	}
}
