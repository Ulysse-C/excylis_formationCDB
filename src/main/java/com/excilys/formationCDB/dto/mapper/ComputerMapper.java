package com.excilys.formationCDB.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.dto.DashBoardComputerDTO;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Computer.ComputerBuilder;

public class ComputerMapper {

	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static Computer createComputer(AddComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().setId(Integer.parseInt(computerDTO.companyId)).build();
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder().setCompany(company).setName(computerDTO.computerName);
		if (computerDTO.introducedDate != "") {
			computerBuilder.setIntroduced(
					LocalDate.parse(computerDTO.introducedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (computerDTO.discontinuedDate != "") {
			computerBuilder.setDiscontinued(
					LocalDate.parse(computerDTO.discontinuedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		return computerBuilder.build();
	}

	public static List<DashBoardComputerDTO> createDashBoardComputerDTOList(List<Optional<Computer>> list) {
		List<DashBoardComputerDTO> result = new ArrayList<>();
		for (Optional<Computer> computer : list) {
			result.add(createDashBoardComputerDTO(computer.get()));
		}
		return result;
	}

	public static DashBoardComputerDTO createDashBoardComputerDTO(Computer computer) {
		DashBoardComputerDTO computerDTO = new DashBoardComputerDTO();
		computerDTO.computerName = computer.getName();
		computerDTO.computerId = String.valueOf(computer.getId());
		if (computer.getCompany() != null) {
			computerDTO.companyName = computer.getCompany().getName();
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.discontinuedDate = computer.getDiscontinued().toString();
		}
		if (computer.getIntroduced() != null) {
			computerDTO.introducedDate = computer.getIntroduced().toString();
		}
		return computerDTO;
	}
}