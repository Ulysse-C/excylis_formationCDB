package com.excilys.formationcdb.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formationcdb.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Company.CompanyBuilder().setId(resultSet.getInt("id")).setName(resultSet.getString("name")).build();
	}
}
