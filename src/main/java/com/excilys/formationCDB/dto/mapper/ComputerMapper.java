package com.excilys.formationCDB.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.dto.DashBoardComputerDTO;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Computer.ComputerBuilder;

public class ComputerMapper {

	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static Computer createComputer(AddComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder(Integer.parseInt(computerDTO.companyId)).build();
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder().setCompany(company).setName(computerDTO.computerName);
		if (computerDTO.introducedDate != "") {
			computerBuilder.setIntroduced(
					LocalDate.parse(computerDTO.introducedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (computerDTO.discontinuedDate != "") {
			computerBuilder.setIntroduced(
					LocalDate.parse(computerDTO.discontinuedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		System.out.println(computerBuilder.build().toString());
		return computerBuilder.build();
	}

	public static List<DashBoardComputerDTO> createDashBoardComputerDTOList(List<Computer> content) {
		List<DashBoardComputerDTO> result = new ArrayList<>();
		for (Computer computer : content) {
			result.add(createDashBoardComputerDTO(computer));
		}
		return result;
	}

	private static DashBoardComputerDTO createDashBoardComputerDTO(Computer computer) {
		DashBoardComputerDTO computerDTO = new DashBoardComputerDTO();
		computerDTO.companyName = computer.getCompany().getName();
		computerDTO.computerName = computer.getName();
		if (computer.getDiscontinued() != null) {
			computerDTO.discontinuedDate = computer.getDiscontinued().toString();

		}
		if (computer.getIntroduced() != null) {
			computerDTO.introducedDate = computer.getIntroduced().toString();
		}
		return computerDTO;
	}
}