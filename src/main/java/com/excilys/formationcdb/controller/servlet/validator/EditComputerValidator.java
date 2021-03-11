package com.excilys.formationcdb.controller.servlet.validator;

import java.util.Map;

import com.excilys.formationcdb.dto.EditComputerDTO;

public interface EditComputerValidator {

	Map<String, String> validate(EditComputerDTO computerDTO);

}
