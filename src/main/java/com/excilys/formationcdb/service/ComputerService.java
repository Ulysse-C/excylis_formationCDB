package com.excilys.formationcdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.dao.DaoComputer;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

@Component
@Scope("singleton")
public class ComputerService {
	
	@Autowired
	private DaoComputer daoComputer;

	public Optional<Computer> getComputerById(int id) {
		return daoComputer.getComputerById(id);
	}

	public void createComputer(Computer computer) {
		daoComputer.createComputer(computer);
	}

	public void updateComputer(Computer computer) throws  NothingSelectedException {
		daoComputer.updateComputer(computer);
	}

	public void deleteComputerById(int i) throws NothingSelectedException {
		daoComputer.deleteComputerById(i);
	}

	public Page<Computer> getPage(Page<Computer> page)  {
		return daoComputer.getPage(page);
	}

	public int getComputerNumber() {
		return daoComputer.getComputerNumber();
	}

	public int getComputerNumberbyName(String search) {
		return daoComputer.getComputerNumberbyName(search);
	}

	public Page<Computer> getPageByName(Page<Computer> page, String search)  {
		return daoComputer.getPageByName(page, search);
	}



}
