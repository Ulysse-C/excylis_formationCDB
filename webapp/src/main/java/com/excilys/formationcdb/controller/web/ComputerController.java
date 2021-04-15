package com.excilys.formationcdb.controller.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
import com.excilys.formationcdb.controller.web.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.DashBoardComputerDTO;
import com.excilys.formationcdb.dto.web.EditComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.model.Page.PageBuilder;
import com.excilys.formationcdb.model.Page.SortAttribute;
import com.excilys.formationcdb.model.Page.SortOrder;
import com.excilys.formationcdb.service.ComputerService;

@RestController
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/computer")
public class ComputerController {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService;
	private AddComputerValidator addValidator;
	private EditComputerValidator editValidator;

	public ComputerController( ComputerService computerService,
			AddComputerValidator addValidator, EditComputerValidator editValidator) {
		this.computerService = computerService;
		this.addValidator = addValidator;
		this.editValidator = editValidator;
	}

	@GetMapping(value = "/list/{pageNumber}", produces = "application/json")
	public ResponseEntity<List<DashBoardComputerDTO>> getListDto(@PathVariable int pageNumber,
			@RequestParam(required = false) String search, @RequestParam(required = false) SortAttribute orderAttribute,
			@RequestParam(required = false) SortOrder orderOrder, @RequestParam(required = false) Integer pageSize) {
		PageBuilder pageBuilder = new Page.PageBuilder();
		pageBuilder.setNumber(pageNumber);
		pageBuilder.setSearch(search);
		pageBuilder.setSortName(orderAttribute);
		pageBuilder.setSortOrder(orderOrder);
		pageBuilder.setSize(pageSize);
		Page<Computer> page = pageBuilder.build();
		System.out.println(page.getSize());
		List<Computer> list = computerService.getPage(page).getContent();
		return ResponseEntity.ok(WebComputerMapper.createDashBoardComputerDTOList(list));
	}

	@GetMapping(value = "/count", produces = "application/json")
	public ResponseEntity<Integer> getComputerNumber(@RequestParam(required = false) String search) {
		String searchAttribute = "";
		if (search != null) {
			searchAttribute = search;
		}
		CDBLogger.logInfo(this.getClass(), "test File");
		return ResponseEntity.ok(computerService.getComputerNumberbyName(searchAttribute));
	}
	
	@GetMapping(value = "/getTen", produces = "application/json")
	public ResponseEntity<Integer> getTen() {
		return ResponseEntity.ok(10);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteComputer(@RequestParam String delete) {
		String[] deleteList = delete.split(",");
		List<String> list = Arrays.asList(deleteList);
		list.stream().forEach(id -> {
			try {
				computerService.deleteComputerById(Integer.valueOf(id));
			} catch (NumberFormatException e) {
				CDBLogger.logError(CompanyController.class, e);
			} catch (NothingSelectedException e) {
				CDBLogger.logError(CompanyController.class,e);
			}
		});
		return ResponseEntity.ok("deleted");
	}
	
	

	@PostMapping(value = "/add")
	protected ResponseEntity<Map<String, String>> postAddComputer(@RequestBody AddComputerDTO addComputerDTO) {
		Map<String, String> errors = addValidator.validate(addComputerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(addComputerDTO);
			computerService.createComputer(computer);
		}
		return new ResponseEntity<>(errors, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/edit")
	protected ResponseEntity<Map<String, String>> getEditComputer(@RequestBody EditComputerDTO editComputerDTO) {
		Map<String, String> errors = editValidator.validate(editComputerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(editComputerDTO);
			computerService.createComputer(computer);
		}
		return ResponseEntity.ok(errors);
	}


}
