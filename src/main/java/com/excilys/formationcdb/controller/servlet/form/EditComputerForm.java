package com.excilys.formationcdb.controller.servlet.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationcdb.dto.EditComputerDTO;

public interface EditComputerForm {

	EditComputerDTO editComputer(HttpServletRequest request);

	Map<String, String> getErrors();

}
