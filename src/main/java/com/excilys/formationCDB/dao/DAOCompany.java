package com.excilys.formationCDB.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Page;

public final class DAOCompany {
	
	private DBConnection dbConnection;
	private final static DAOCompany INSTANCE = new DAOCompany();
	
	private String getCompanyByIdQuery = "SELECT id,name FROM company WHERE id=?";
	private String getPageQuery = "SELECT id,name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private DAOCompany() { 
		dbConnection = DBConnection.getInstance();
	}

	public static DAOCompany getInstance() {
		return INSTANCE;
	}

	public Company getCompanyById(int id) throws CustomSQLException {
		Company company = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getCompanyByIdQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				company = convertToCompany(resultSet);
			}
		} catch (SQLException sqlException) {
			throw new CustomSQLException(sqlException.getMessage());
		}
		return company;
	}
	
	private ArrayList<Company> convertToListCompanyList(ResultSet resultSetCompanyList) throws CustomSQLException, SQLException {
		ArrayList<Company> companyList = new ArrayList<>();
			while (resultSetCompanyList.next()) {
				companyList.add(convertToCompany(resultSetCompanyList));
			}
		return companyList;
	}
	
	private Company convertToCompany(ResultSet resultSetCompany) throws CustomSQLException, SQLException {
		Company company = null;
			company = new Company(resultSetCompany.getNString("name"),resultSetCompany.getInt("id"));


		return company;
	}

	public Page<Company> getPage(Page<Company> page) throws CustomSQLException {
		if (page != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(getPageQuery);
				preparedStatement.setInt(1, page.getSize());
				preparedStatement.setInt(2, (page.getNumber()-1)*page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListCompanyList(resultSet));
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
		return page;
	}
}
