package com.excilys.formationcdb.controller.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
import com.excilys.formationcdb.controller.web.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.WebCompanyDTO;
import com.excilys.formationcdb.dto.web.mapper.WebCompanyMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private CompanyService companyService;

	public CompanyController(CompanyService companyService, AddComputerValidator addValidator,
			EditComputerValidator editValidator) {
		this.companyService = companyService;
	}

	@GetMapping(value = "/list")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public ResponseEntity<List<WebCompanyDTO>> getListDto() {
		return ResponseEntity.ok(WebCompanyMapper.createAddCompanyDTOList(companyService.getCompanyList()));
	}

	@GetMapping(value = "/delete")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public ResponseEntity<String> delete(@RequestParam int id) {
		ResponseEntity<String> response = ResponseEntity.ok("deleted");
		try {
			companyService.deleteCompanyById(id);
		} catch (NothingSelectedException e) {
			CDBLogger.logError(CompanyController.class, e);
			response = ResponseEntity.badRequest().body("no company with the id: " + id);
		}
		ResponseEntity.badRequest();
		return response;
	}
}
