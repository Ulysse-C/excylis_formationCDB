package com.excilys.formationCDB.controller.cli;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidInputCLIHandlerException;
import com.excilys.formationCDB.exception.NothingSelectedException;
import com.excilys.formationCDB.logger.CDBLogger;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public class CLIHandler {

	private CliCompanyController companyController;
	private CliComputerController computerController;

	public CLIHandler() {
		this.companyController = CliCompanyController.getInstance();
		this.computerController = CliComputerController.getInstance();
	}

	public Optional<Computer> getSingleComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException {
		Optional<Computer> computer;
		if (inputList.length != 2) {
			throw new InvalidInputCLIHandlerException("Wrong number of arguments");
		}
		if (isInteger(inputList[1], 10)) {
			computer = computerController.getComputerById(Integer.parseInt(inputList[1]));
		} else {
			throw new InvalidInputCLIHandlerException("Wrong argument type");
			// computer = computerController.getComputerByName(inputList[1]);
		}
		return computer;
	}

	public void createComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException, CompanyKeyInvalidException {
		if (inputList.length != 3 && inputList.length != 5) {
			throw new InvalidInputCLIHandlerException("Wrong number of arguments");
		}
		Computer computer = null;
		if (inputList.length == 3 && isInteger(inputList[2], 10)) {
			AddComputerDTO computerDTO = createComputerDTO(inputList);
			// the mapper will not parse the date with this format yet
			computer = ComputerMapper.createComputer(computerDTO);
		} else if (inputList.length == 5 && isInteger(inputList[2], 10)) {
			AddComputerDTO computerDTO = createComputerDTO(inputList);
			computer = ComputerMapper.createComputer(computerDTO);
		} else {
			throw new InvalidInputCLIHandlerException("Wrong argument type");
		}
		computerController.createComputer(computer);
	}

	private AddComputerDTO createComputerDTO(String[] inputList) {
		AddComputerDTO computerDTO = new AddComputerDTO();
		computerDTO.companyId = inputList[2];
		computerDTO.computerName = inputList[1];
		if (inputList.length == 5) {
			computerDTO.introducedDate = inputList[3];
			computerDTO.discontinuedDate = inputList[4];
		}
		return computerDTO;
	}

	public void updateComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException, NothingSelectedException {
		Computer computer;
		if (inputList.length != 3 && inputList.length != 5) {
			throw new InvalidInputCLIHandlerException("Wrong number of arguments");
		}
		if (inputList.length == 3 && isInteger(inputList[1], 10)) {
			computer = new Computer.ComputerBuilder().setName(inputList[2]).setId(Integer.parseInt(inputList[1]))
					.build();
			computerController.updateComputer(computer);
		} else if (inputList.length == 5 && isInteger(inputList[1], 10)) {
			computer = new Computer.ComputerBuilder().setName(inputList[2]).setId(Integer.parseInt(inputList[1]))
					.build();
			computer.setIntroduced(LocalDate.parse(inputList[3]));
			computer.setDiscontinued(LocalDate.parse(inputList[4]));
			computerController.updateComputer(computer);
		} else {
			throw new InvalidInputCLIHandlerException("Wrong argument type");
		}

	}

	public void deleteComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException, NothingSelectedException {
		Computer computer = null;
		if (inputList.length != 2) {
			throw new InvalidInputCLIHandlerException("Wrong number of arguments");
		} else if (!isInteger(inputList[1], 10)) {
			throw new InvalidInputCLIHandlerException("Wrong argument type");
		}
		computerController.deleteComputerById(Integer.parseInt(inputList[1]));
	}

	// https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public Page getPage(Page page) throws CustomSQLException {
		Page pageResult = null;
		if (page.getTable().equals("computer")) {
			pageResult = computerController.getPage(page);
		} else if (page.getTable().equals("company")) {
			pageResult = companyController.getPage(page);
		}
		return pageResult;
	}

	public void deleteCompany(String[] inputList) throws InvalidInputCLIHandlerException {
		if (inputList.length == 2) {
			try {
				companyController.deleteCompanyById(Integer.parseInt(inputList[1]));
			} catch (NumberFormatException e) {
				CDBLogger.logError(e);
			} catch (NothingSelectedException e) {
				CDBLogger.logError(e);
			}
		} else {
			throw new InvalidInputCLIHandlerException("To many arguments");
		}
	}

}
