package com.excilys.formationcdb.service;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.formationcdb.dao.ComputerDao;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

@Service
@Scope("singleton")
public class ComputerService {

	private ComputerDao daoComputer;

	public ComputerService(ComputerDao daoComputer) {
		this.daoComputer = daoComputer;
	}

	public Optional<Computer> getComputerById(int id) {
		return daoComputer.getComputerById(id);
	}

	public void createComputer(Computer computer) {
		daoComputer.createComputer(computer);
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		daoComputer.updateComputer(computer);
	}

	public void deleteComputerById(int i) throws NothingSelectedException {
		daoComputer.deleteComputerById(i);
	}

	public Page getPage(Page page) {
		return daoComputer.getPage(page);
	}

	public int getComputerNumberbyName(String search) {
		return daoComputer.getComputerNumberbyName(search);
	}

}
