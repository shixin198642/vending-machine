package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.utils.RequestUtils;
import com.mjitech.websocket.SessionHolders;

@Controller
public class PayController {
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionHolders sessionHolders;
	@RequestMapping(value = WebPageConstants.PAY_PAGE)
	public ModelAndView loginPage(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.PAY_VIEW);
		String money = requestUtils.getStringValue(params, "money");
		int count = requestUtils.getIntValue(params, "count");
		if(count==0){
			count = 1;
		}
		if(StringUtils.isEmpty(money)){
			money = "100.0";
		}
		mv.addObject("money", money);
		mv.addObject("count", count);
		sessionHolders.broadMessages("pay");
		return mv;
	}
	
	@RequestMapping(value = WebPageConstants.PAY_SUBMIT)
	public ModelAndView paySubmit(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.PAY_RESULT_VIEW);
		sessionHolders.broadMessages("paid");
		return mv;
	}
}
