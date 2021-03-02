package com.excilys.formationCDB.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.formationCDB.dto.DashBoardComputerDTO;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;

public class ComputerMapperTest {

	@Test
	public void testCreateDashBoardComputerDTO() {
		Company company = new Company.CompanyBuilder().setName("companyName").build();
		LocalDate dateIntro = LocalDate.of(2021, 10, 5);
		LocalDate dateDiscon = LocalDate.of(2021, 12, 5);
		Computer computer = new Computer.ComputerBuilder().setId(1).setIntroduced(dateIntro).setDiscontinued(dateDiscon)
				.setName("computerName").setCompany(company).build();
		DashBoardComputerDTO computerDTO = ComputerMapper.createDashBoardComputerDTO(computer);
		assertEquals("companyName", computerDTO.companyName);
		assertEquals("computerName", computerDTO.getComputerName());
		assertEquals(dateIntro.toString(), computerDTO.getIntroducedDate());
		assertEquals(dateDiscon.toString(), computerDTO.getDiscontinuedDate());
	}
}
