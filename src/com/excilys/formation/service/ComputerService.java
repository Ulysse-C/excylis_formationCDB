package com.excilys.formation.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dao.DAOComputer;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerService {

	private static ComputerService computerService;
	private DAOComputer daoComputer;
	private CompanyService companyService;

	private ComputerService() {
		daoComputer = DAOComputer.getDaoComputer();
		companyService = CompanyService.getCompanyService();
	}

	public static ComputerService getComputerService() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	public  List<Computer> listComputer() {
		return daoComputer.listComputer();
	}

	public Computer getComputerById(int id) {
		return daoComputer.getComputerById(id);
	}

	public Computer getComputerByName(String computerName) {
		return daoComputer.getComputerByName(computerName);
	}

	

	public boolean createComputer(Computer computer) {
		return daoComputer.createComputer(computer);

	}

	public boolean updateComputerName(Computer computer) {
		return daoComputer.updateComputerName(computer);
	}

	public boolean deleteComputerById(Computer computer) {
		return daoComputer.deleteComputerById(computer);
	}

}
