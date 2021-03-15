package com.excilys.formationcdb.controller.servlet;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EditComputerRequestVariable {
	private ModelAndView modelAndView = new ModelAndView();
	
	public ModelAndView getModelAndView() {
		return modelAndView;
	}

}