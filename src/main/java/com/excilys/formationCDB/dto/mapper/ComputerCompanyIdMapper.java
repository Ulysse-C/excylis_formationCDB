package com.excilys.formationCDB.dto.mapper;

import com.excilys.formationCDB.dto.ComputerCompanyIdDTO;
import com.excilys.formationCDB.model.Computer;

public class ComputerCompanyIdMapper {
	public static Computer createComputer(ComputerCompanyIdDTO computerdto) {
		Computer computer = new Computer();
		return computer;
	}
}
