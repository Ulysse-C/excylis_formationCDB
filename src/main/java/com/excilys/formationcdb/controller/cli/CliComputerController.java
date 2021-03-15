package com.excilys.formationcdb.controller.cli;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.exception.CompanyKeyInvalidException;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.service.ComputerService;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CliComputerController {

	public static final  int PAGE_SIZE = 10;
	public static final  String COMPUTER_TABLE_NAME = "computer";

	private ComputerService computerService;

	public CliComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	public Optional<Computer> getComputerById(int id) {
		return computerService.getComputerById(id);
	}

	public void createComputer(Computer computer) throws CompanyKeyInvalidException {
		computerService.createComputer(computer);

	}

	public void deleteComputerById(int id) throws NothingSelectedException {
		computerService.deleteComputerById(id);
	}

	public Page getPage(Page page) {
		return computerService.getPage(page);
	}

	public int getComputerNumber() {
		return computerService.getComputerNumberbyName("");
	}
	public int getComputerNumberbyName(String search) throws CustomSQLException {
		return computerService.getComputerNumberbyName(search);
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		computerService.updateComputer(computer);

	}

}
