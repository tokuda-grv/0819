package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestPageController {
	
	@Autowired
	private TestPageService tps;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView top(ModelAndView mav) {
		return tps.top(mav);
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public ModelAndView test(ModelAndView mav) throws IOException {
		return tps.test(mav);
	}
	
	@RequestMapping(value="/sample", method = RequestMethod.GET)
	public ModelAndView sample(ModelAndView mav) {
		return tps.sample(mav);
	}

}
