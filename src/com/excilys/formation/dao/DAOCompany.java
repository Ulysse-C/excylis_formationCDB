package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.model.Company;

public class DAOCompany {
	
	private DBConnection dbConnection;
	private static DAOCompany daoCompany;
	
	private String listCompanyQuery = "SELECT id,name FROM company";
	private String getCompanyByIdQuery = "SELECT id,name FROM company WHERE id=?";
	private DAOCompany() { 
		dbConnection = DBConnection.getDaoConnection();
	}

	public static DAOCompany getDaoCompany() {
		if (daoCompany == null) {
			daoCompany = new DAOCompany();
		}
		return daoCompany;
	}
	
	public List<Company> listCompany() {
		ArrayList<Company> companyList = new ArrayList<>();
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(listCompanyQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				companyList = convertToListCompanyList(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	public Company getCompanyById(int id) {
		Company company = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getCompanyByIdQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				company = convertToCompany(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
	
	private ArrayList<Company> convertToListCompanyList(ResultSet resultSetCompanyList) {
		ArrayList<Company> companyList = new ArrayList<>();
		try {
			while (resultSetCompanyList.next()) {
				companyList.add(convertToCompany(resultSetCompanyList));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companyList;
	}
	
	private Company convertToCompany(ResultSet resultSetCompany) {
		Company company = null;
		try {
			company = new Company(resultSetCompany.getNString("name"),resultSetCompany.getInt("id"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}
}
