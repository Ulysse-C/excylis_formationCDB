package com.excilys.formation.controller;

import java.util.List;

import com.excilys.formation.model.Computer;
import com.excilys.formation.service.ComputerService;

public class ComputerController {
	private ComputerService computerService;

	public ComputerController() {
		computerService = ComputerService.getComputerService();
	}

	public List<Computer> listComputer() {
		return computerService.listComputer();
	}

	public Computer getComputerById(int id) {
		return computerService.getComputerById(id);
	}

	public Computer getComputerByName(String computerName) {
		return computerService.getComputerByName(computerName);
	}

	public boolean createComputer(Computer computer) {
		return computerService.createComputer(computer);

	}

	public boolean updateComputerName(Computer computer) {
		return computerService.updateComputerName(computer);
	}

	public boolean deleteComputerById(Computer computer) {
		return computerService.deleteComputerById(computer);
	}
}
