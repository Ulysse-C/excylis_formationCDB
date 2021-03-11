package com.excilys.formationcdb.controller.cli;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.dto.AddComputerDTO;
import com.excilys.formationcdb.dto.mapper.ComputerMapper;
import com.excilys.formationcdb.exception.CompanyKeyInvalidException;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.InvalidInputCLIHandlerException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

@Component
@Scope("singleton")
public class CliHandlerImpl implements CliHandler {

	@Autowired
	private CliCompanyController cliCompanyController;
	@Autowired
	private CliComputerController cliComputerController;

	public Optional<Computer> getSingleComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException {
		Optional<Computer> computer;
		if (inputList.length != 2) {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_NUMBER_OF_ARGS);
		}
		if (isInteger(inputList[1], 10)) {
			computer = cliComputerController.getComputerById(Integer.parseInt(inputList[1]));
		} else {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_ARGUMENT_TYPE);
		}
		return computer;
	}

	public void createComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException, CompanyKeyInvalidException {
		if (inputList.length != 3 && inputList.length != 5) {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_NUMBER_OF_ARGS);
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
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_ARGUMENT_TYPE);
		}
		cliComputerController.createComputer(computer);
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
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_NUMBER_OF_ARGS);
		}
		if (inputList.length == 3 && isInteger(inputList[1], 10)) {
			computer = new Computer.ComputerBuilder().setName(inputList[2]).setId(Integer.parseInt(inputList[1]))
					.build();
			cliComputerController.updateComputer(computer);
		} else if (inputList.length == 5 && isInteger(inputList[1], 10)) {
			computer = new Computer.ComputerBuilder().setName(inputList[2]).setId(Integer.parseInt(inputList[1]))
					.build();
			computer.setIntroduced(LocalDate.parse(inputList[3]));
			computer.setDiscontinued(LocalDate.parse(inputList[4]));
			cliComputerController.updateComputer(computer);
		} else {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_ARGUMENT_TYPE);
		}

	}

	public void deleteComputer(String[] inputList)
			throws InvalidInputCLIHandlerException, CustomSQLException, NothingSelectedException {
		if (inputList.length != 2) {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_NUMBER_OF_ARGS);
		} else if (!isInteger(inputList[1], 10)) {
			throw new InvalidInputCLIHandlerException(InvalidInputCLIHandlerException.WRONG_ARGUMENT_TYPE);
		}
		cliComputerController.deleteComputerById(Integer.parseInt(inputList[1]));
	}

	private boolean isInteger(String s, int radix) {
		if (s.isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if (Character.digit(s.charAt(i), radix) < 0) {
				return false;
			}
		}
		return true;
	}

	public <E> Page<E> getPage(Page<E> page) {
		Page pageResult = null;
		if (page.getTable().equals("computer")) {
			pageResult = cliComputerController.getPage(page);
		} else if (page.getTable().equals("company")) {
			pageResult = cliCompanyController.getPage(page);
		}
		return pageResult;
	}

	public void deleteCompany(String[] inputList) throws InvalidInputCLIHandlerException {
		if (inputList.length == 2) {
			try {
				cliCompanyController.deleteCompanyById(Integer.parseInt(inputList[1]));
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
