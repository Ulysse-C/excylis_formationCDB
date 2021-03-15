package com.excilys.formationcdb.controller.web.validator;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.excilys.formationcdb.controller.web.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.EditComputerDTO;

public class EditComputerValidatorTest {

	@Test
	public void testValidateName() {
		EditComputerValidator validator = new EditComputerValidator();
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.companyId = "1";
		computerDTO.computerName = "ok";
		Map<String, String> errors = validator.validate(computerDTO);
		assertEquals(true, errors.isEmpty());
	}

	@Test
	public void testValidateCompanyId() {
		EditComputerValidator validator = new EditComputerValidator();
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.companyId = "1";
		computerDTO.computerName = "ok";
		Map<String, String> errors = validator.validate(computerDTO);
		assertEquals(true, errors.isEmpty());
		computerDTO.companyId = "";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_COMPANYID));
		computerDTO.companyId = "500";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_COMPANYID));
		computerDTO.companyId = "0";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_COMPANYID));
	}

	@Test
	public void testValidateDate() {
		EditComputerValidator validator = new EditComputerValidator();
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.companyId = "1";
		computerDTO.computerName = "ok";
		computerDTO.discontinuedDate = "54646546546";
		computerDTO.introducedDate = "";
		Map<String, String> errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_INTRODUCED));
		computerDTO.discontinuedDate = "";
		computerDTO.introducedDate = "854625";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_INTRODUCED));
		computerDTO.discontinuedDate = "2021-01-20";
		computerDTO.introducedDate = "2021-12-20";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.containsKey(EditComputerValidator.INPUT_INTRODUCED));
		computerDTO.discontinuedDate = "2021-12-20";
		computerDTO.introducedDate = "2021-01-20";
		errors = validator.validate(computerDTO);
		assertEquals(true, errors.isEmpty());
	}
}
