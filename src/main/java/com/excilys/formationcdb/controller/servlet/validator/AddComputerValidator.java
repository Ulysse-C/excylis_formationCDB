package com.excilys.formationcdb.controller.servlet.validator;

import java.util.Map;

import com.excilys.formationcdb.dto.AddComputerDTO;

public interface AddComputerValidator {

	Map<String, String> validate(AddComputerDTO computerDTO);

}
