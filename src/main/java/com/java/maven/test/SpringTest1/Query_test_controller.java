package com.java.maven.test.SpringTest1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Query_test_controller {
	
	@RequestMapping(value="/query_test", method=RequestMethod.GET)
	public String query_test_Form(Model model){
		model.addAttribute("query_test",  new Query_test());
		return "query_test";
	}
	
	@RequestMapping(value="/query_test", method=RequestMethod.POST)
	public String query_test_Submit(@ModelAttribute Query_test query_test,Model model){
		model.addAttribute("query_test",  query_test);
		return "query_test_result";
	}
	
}
