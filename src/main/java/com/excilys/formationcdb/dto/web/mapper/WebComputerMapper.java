package com.excilys.formationcdb.dto.web.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.DashBoardComputerDTO;
import com.excilys.formationcdb.dto.web.EditComputerDTO;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Computer.ComputerBuilder;

public class WebComputerMapper {

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static Computer createComputer(AddComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().setId(Integer.parseInt(computerDTO.companyId)).build();
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder().setCompany(company).setName(computerDTO.computerName);
		if (!"".equals(computerDTO.introducedDate)) {
			computerBuilder.setIntroduced(
					LocalDate.parse(computerDTO.introducedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (!"".equals(computerDTO.discontinuedDate)) {
			computerBuilder.setDiscontinued(
					LocalDate.parse(computerDTO.discontinuedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		return computerBuilder.build();
	}
	
	public static Computer createComputer(EditComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().setId(Integer.parseInt(computerDTO.companyId)).setName(computerDTO.companyName).build();
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder().setId(Integer.parseInt(computerDTO.computerId)).setCompany(company).setName(computerDTO.computerName);
		if (!"".equals(computerDTO.introducedDate)) {
			computerBuilder.setIntroduced(
					LocalDate.parse(computerDTO.introducedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (!"".equals(computerDTO.discontinuedDate)) {
			computerBuilder.setDiscontinued(
					LocalDate.parse(computerDTO.discontinuedDate, DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		return computerBuilder.build();
	}

	public static List<DashBoardComputerDTO> createDashBoardComputerDTOList(List<Optional<Computer>> list) {
		List<DashBoardComputerDTO> result = new ArrayList<>();
		for (Optional<Computer> computer : list) {
			result.add(createDashBoardComputerDTO(computer));
		}
		return result;
	}

	private static DashBoardComputerDTO createDashBoardComputerDTO(Optional<Computer> computer) {
		DashBoardComputerDTO computerDTO = new DashBoardComputerDTO();
		if (computer.isPresent()) {
			computerDTO = createDashBoardComputerDTO(computer.get());
		}
		return computerDTO;
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
	
	
	public static EditComputerDTO createEditComputerDTO(Computer computer) {
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.computerName = computer.getName();
		computerDTO.computerId = String.valueOf(computer.getId());
		if (computer.getCompany() != null) {
			computerDTO.companyName = computer.getCompany().getName();
			computerDTO.companyId = String.valueOf(computer.getCompany().getId());
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.discontinuedDate = computer.getDiscontinued().toString();
		}
		if (computer.getIntroduced() != null) {
			computerDTO.introducedDate = computer.getIntroduced().toString();
		}
		return computerDTO;
	}

	public static EditComputerDTO createEditComputerDTO(Optional<Computer> computerById) {
		EditComputerDTO computerDTO = new EditComputerDTO();
		if (computerById.isPresent()) {
			computerDTO = createEditComputerDTO(computerById.get());
		}
		return computerDTO;
	}
}