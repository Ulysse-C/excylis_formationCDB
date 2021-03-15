package com.excilys.formationcdb.dao.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(resultSet.getInt("computer.company_id"))
				.setName(resultSet.getNString("company.name")).build();
		Computer computer = new Computer.ComputerBuilder().setName(resultSet.getString("computer.name"))
				.setId(resultSet.getInt("computer.id")).setCompany(company)
				.setDiscontinued(toLocalDate(resultSet.getDate("computer.discontinued")))
				.setIntroduced(toLocalDate(resultSet.getDate("computer.introduced"))).build();
		return computer;
	}

	private LocalDate toLocalDate(Date date) {
		LocalDate localDate = null;
		if (date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}
}
