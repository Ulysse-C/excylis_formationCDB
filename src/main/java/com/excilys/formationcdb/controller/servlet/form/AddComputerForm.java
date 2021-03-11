package com.excilys.formationcdb.controller.servlet.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationcdb.dto.AddComputerDTO;

public interface AddComputerForm {

	AddComputerDTO addComputer(HttpServletRequest request);

	Map<String, String> getErrors();

}
