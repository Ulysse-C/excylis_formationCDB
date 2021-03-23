package com.excilys.formationcdb.dto.dao.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.formationcdb.dto.dao.ComputerPersist;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Computer.ComputerBuilder;

public class DaoComputerMapper extends DaoMapper {
	public static List<Computer> toComputerList(List<ComputerPersist> computerListPersist) {
		return computerListPersist.stream().map(DaoComputerMapper::toComputer).collect(Collectors.toList());
	}

	public static Computer toComputer(ComputerPersist computerPersist) {
		Computer computer = null;
		if (computerPersist != null) {
			ComputerBuilder computerBuilder = new Computer.ComputerBuilder();
			computerBuilder.setDiscontinued(computerPersist.getDiscontinued())
					.setIntroduced(computerPersist.getIntroduced()).setId(computerPersist.getId())
					.setName(computerPersist.getName());
			if (computerPersist.getCompany() != null) {
				computerBuilder.setCompany(DaoCompanyMapper.toCompany(computerPersist.getCompany()));
			}
			computer = computerBuilder.build();
		}
		return computer;
	}

	public static ComputerPersist toComputerPersist(Computer computer) {
		ComputerPersist computerPersist = new ComputerPersist();
		computerPersist.setCompanyId(computer.getCompanyId());
		computerPersist.setDiscontinued(computer.getDiscontinued());
		computerPersist.setIntroduced(computer.getIntroduced());
		computerPersist.setName(computer.getName());
		computerPersist.setId(computer.getId());
		return computerPersist;
	}
}
