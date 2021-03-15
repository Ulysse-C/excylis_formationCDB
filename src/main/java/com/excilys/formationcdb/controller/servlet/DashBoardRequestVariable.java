package com.excilys.formationcdb.controller.servlet;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DashBoardRequestVariable {
	private ModelAndView modelAndView = new ModelAndView();
	private int pageNumber = 1;
	private int computerNumber = 0;
	private String search = "";


	public ModelAndView getModelAndView() {
		return modelAndView;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getComputerNumber() {
		return computerNumber ;
	}


	public void setComputerNumber(int computerNumber) {
		this.computerNumber = computerNumber;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}
}
 