package com.excilys.formationcdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class DaoCompany {

//	@Autowired
	private DbConnection dbConnection = DbConnection.getInstance();
	
	
	private static final String DELETE_COMPUTER_OF_COMPANY_QUERY = "DELETE FROM computer WHERE company_id = ?";
	private static final String DELETE_COMPANY_BY_ID_QUERY = "DELETE FROM company WHERE id = ?";

	private static final String GET_COMPANY_BY_ID_QUERY = "SELECT id,name FROM company WHERE id=?";
	private static final String GET_PAGE_QUERY = "SELECT id,name FROM company ORDER BY id LIMIT ? OFFSET ?";
	private static final String GET_COMPANY_LIST_QUERY = "SELECT id,name FROM company ORDER BY id";


	public Optional<Company> getCompanyById(int id) {
		Optional<Company> company = Optional.empty();
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY_BY_ID_QUERY)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				company = convertToCompany(resultSet);
			}
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return company;
	}

	private List<Optional<Company>> convertToListCompanyList(ResultSet resultSetCompanyList) throws SQLException {
		ArrayList<Optional<Company>> companyList = new ArrayList<>();
		while (resultSetCompanyList.next()) {
			companyList.add(convertToCompany(resultSetCompanyList));
		}
		return companyList;
	}

	private Optional<Company> convertToCompany(ResultSet resultSetCompany) throws SQLException {
		return Optional.ofNullable(new Company.CompanyBuilder().setId(resultSetCompany.getInt("id"))
				.setName(resultSetCompany.getNString("name")).build());
	}

	public Page<Company> getPage(Page<Company> page) {
		if (page != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection.prepareStatement(GET_PAGE_QUERY)) {
				preparedStatement.setInt(1, page.getSize());
				preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListCompanyList(resultSet));
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
		return page;
	}

	public List<Optional<Company>> getCompanyList() {
		List<Optional<Company>> companyList = new ArrayList<>();
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY_LIST_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			companyList = convertToListCompanyList(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return companyList;
	}

	public void deleteCompanyById(int id) throws NothingSelectedException {
		Connection connection = null;
		PreparedStatement deleteComputer = null;
		PreparedStatement deleteCompany = null;
		try {
			connection = dbConnection.getconnection();
			connection.setAutoCommit(false);
			deleteComputer = dbConnection.getconnection().prepareStatement(DELETE_COMPUTER_OF_COMPANY_QUERY);
			deleteComputer.setInt(1, id);
			deleteComputer.executeUpdate();
			deleteCompany = dbConnection.getconnection().prepareStatement(DELETE_COMPANY_BY_ID_QUERY);
			deleteCompany.setInt(1, id);
			int row = deleteCompany.executeUpdate();
			if (row == 0) {
				throw new NothingSelectedException("Update Name");
			}
			connection.commit();
		} catch (SQLException sqlException) {
			try {
				if (connection != null) {
					connection.rollback();
				}
				CDBLogger.logError(sqlException);
			} catch (SQLException sqlException2) {
				CDBLogger.logError(sqlException2);
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);

			}
		}
	}
}