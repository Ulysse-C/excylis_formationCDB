package com.excilys.formationCDB.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Page;

public final class DAOCompany {

	private DBConnection dbConnection;
	private final static DAOCompany INSTANCE = new DAOCompany();

	private String getCompanyByIdQuery = "SELECT id,name FROM company WHERE id=?";
	private String getPageQuery = "SELECT id,name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private String getCompanyListQuery = "SELECT id,name FROM company ORDER BY id";

	private DAOCompany() {
		dbConnection = DBConnection.getInstance();
	}

	public static DAOCompany getInstance() {
		return INSTANCE;
	}

	public Optional<Company> getCompanyById(int id) throws CustomSQLException {
		Optional<Company> company = Optional.empty();
		try (PreparedStatement preparedStatement = dbConnection.getconnection().prepareStatement(getCompanyByIdQuery)) {
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

	private List<Optional<Company>> convertToListCompanyList(ResultSet resultSetCompanyList)
			throws CustomSQLException, SQLException {
		ArrayList<Optional<Company>> companyList = new ArrayList<>();
		while (resultSetCompanyList.next()) {
			companyList.add(convertToCompany(resultSetCompanyList));
		}
		return companyList;
	}

	private Optional<Company> convertToCompany(ResultSet resultSetCompany) throws CustomSQLException, SQLException {
		return Optional.ofNullable(new Company.CompanyBuilder().setId(resultSetCompany.getInt("id"))
				.setName(resultSetCompany.getNString("name")).build());
	}

	public Page<Company> getPage(Page<Company> page) throws CustomSQLException {
		if (page != null) {
			try (PreparedStatement preparedStatement = dbConnection.getconnection().prepareStatement(getPageQuery)) {
				preparedStatement.setInt(1, page.getSize());
				preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListCompanyList(resultSet));
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
		return page;
	}

	public List<Optional<Company>> getCompanyList() throws CustomSQLException {
		List<Optional<Company>> companyList = new ArrayList<>();
		try (PreparedStatement preparedStatement = dbConnection.getconnection().prepareStatement(getCompanyListQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			companyList = convertToListCompanyList(resultSet);
		} catch (SQLException sqlException) {
			throw new CustomSQLException(sqlException.getMessage());
		}
		return companyList;
	}
}
