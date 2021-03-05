package com.excilys.formationcdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

public final class DAOComputer {

	private DBConnection dbConnection;
	private final static DAOComputer INSTANCE = new DAOComputer();
	private static final String UPDATE_COMPUTER_COMPANYID_QUERY = "UPDATE computer "
			+ "SET company_id = ? WHERE id = ?";
	private static final String UPDATE_COMPUTER_DISCONTINUED_QUERY = "UPDATE computer "
			+ "SET discontinued = ? WHERE id = ?";
	private static final String UPDATE_COMPUTER_INTRODUCED_QUERY = "UPDATE computer "
			+ "SET introduced = ? WHERE id = ?";
	private static final String UPDATE_COMPUTER_NAME_QUERY = "UPDATE computer " + "SET name = ? WHERE id = ?";
	private static final String GET_COMPUTER_BY_ID_QUERY = "SELECT computer.id,"
			+ " computer.name, company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id WHERE computer.id = ?";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer"
			+ " (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String DELETE_COMPUTER_BY_ID_QUERY = "DELETE FROM computer" + " WHERE id = ?";
	private static final String GET_PAGE_QUERY = "SELECT computer.id, computer.name,"
			+ " company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id ORDER BY SORT_ATTRIBUTE SORT_ORDER LIMIT ? OFFSET ?";
	private static final String GET_PAGE_BY_NAME_QUERY = "SELECT computer.id, computer.name,"
			+ " company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id WHERE computer.name "
			+ "LIKE ? ORDER BY  SORT_ATTRIBUTE SORT_ORDER LIMIT ? OFFSET ?";
	private static final String GET_COMPUTER_NUMBER_QUERY = "SELECT COUNT(*) AS nbComputer" + " FROM computer";
	private static final String GET_COMPUTER_NUMBER_BY_NAME_QUERY = "SELECT COUNT(name) AS nbComputer"
			+ " FROM computer WHERE computer.name LIKE ?";

	private DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}

	public final static DAOComputer getInstance() {
		return INSTANCE;
	}

	public Optional<Computer> getComputerById(int id) {
		Optional<Computer> computer = Optional.empty();
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPUTER_BY_ID_QUERY)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			computer = getSingleComputerFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return computer;
	}

	private ArrayList<Optional<Computer>> convertToListComputerList(ResultSet resultSetComputerList)
			throws SQLException {
		ArrayList<Optional<Computer>> computerList = new ArrayList<>();
		while (resultSetComputerList.next()) {
			computerList.add(convertToComputer(resultSetComputerList));
		}

		return computerList;
	}

	private Optional<Computer> convertToComputer(ResultSet resultSetComputer) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(resultSetComputer.getInt("computer.company_id"))
				.setName(resultSetComputer.getNString("company.name")).build();
		Computer computer = new Computer.ComputerBuilder().setName(resultSetComputer.getString("computer.name"))
				.setId(resultSetComputer.getInt("computer.id")).setCompany(company)
				.setDiscontinued(toLocalDate(resultSetComputer.getDate("computer.discontinued")))
				.setIntroduced(toLocalDate(resultSetComputer.getDate("computer.introduced"))).build();
		return Optional.ofNullable(computer);
	}

	private LocalDate toLocalDate(Date date) {
		LocalDate localDate = null;
		if (date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}

	public Optional<Computer> getSingleComputerFromResultSet(ResultSet resultSet) throws SQLException {
		Optional<Computer> computer = Optional.empty();
		if (resultSet != null) {
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				computer = convertToComputer(resultSet);
			}
		}
		return computer;
	}

	public void createComputer(Computer computer) {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPUTER_QUERY,
							Statement.RETURN_GENERATED_KEYS)) {
				prepareStatmentCreate(preparedStatement, computer);
				preparedStatement.executeUpdate();
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);

			}
		}
	}

	private void prepareStatmentCreate(PreparedStatement preparedStatement, Computer computer) throws SQLException {
		preparedStatement.setNString(1, computer.getName());
		prepareStatmentDate(preparedStatement, computer.getIntroduced(), 2);
		prepareStatmentDate(preparedStatement, computer.getDiscontinued(), 3);
		preparedStatement.setInt(4, computer.getCompany().getId());
	}

	private void prepareStatmentDate(PreparedStatement preparedStatement, LocalDate localdate, int index)
			throws SQLException {
		if (localdate != null) {
			preparedStatement.setDate(index, Date.valueOf(localdate));
		} else {
			preparedStatement.setNull(index, Types.DATE);
		}
	}

	public void deleteComputerById(int id) throws NothingSelectedException {
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPUTER_BY_ID_QUERY)) {
			preparedStatement.setInt(1, id);
			int row = preparedStatement.executeUpdate();
			if (row == 0) {
				throw new NothingSelectedException("Delete");
			}
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
	}

	private int getComputerNumberFromResultSet(ResultSet resultSet) throws SQLException {
		int number = 0;
		if (resultSet != null) {
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				number = resultSet.getInt("nbComputer");
			}
		}
		return number;
	}

	public int getComputerNumber() {
		int number = 0;
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPUTER_NUMBER_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			number = getComputerNumberFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return number;
	}

	public int getComputerNumberbyName(String search) {
		int number = 0;
		try (Connection connection = dbConnection.getconnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPUTER_NUMBER_BY_NAME_QUERY)) {
			preparedStatement.setString(1, "%" + search + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			number = getComputerNumberFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return number;
	}

	public Page<Computer> getPage(Page<Computer> page) {
		if (page != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection
							.prepareStatement(GET_PAGE_QUERY.replaceFirst("SORT_ORDER", page.getSortOrder().name())
									.replaceFirst("SORT_ATTRIBUTE", page.getSortName().getAttribute()))) {
				preparedStatement.setInt(1, page.getSize());
				preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListComputerList(resultSet));
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
		return page;
	}

	public Page<Computer> getPageByName(Page<Computer> page, String search) {
		if (page != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection.prepareStatement(
							GET_PAGE_BY_NAME_QUERY.replaceFirst("SORT_ORDER", page.getSortOrder().name())
									.replaceFirst("SORT_ATTRIBUTE", page.getSortName().getAttribute()))) {
				preparedStatement.setString(1, "%" + search + "%");
				preparedStatement.setInt(2, page.getSize());
				preparedStatement.setInt(3, (page.getNumber() - 1) * page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListComputerList(resultSet));
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
		return page;
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		if (computer != null) {
			updateComputerName(computer);
			updateComputerIntroduced(computer);
			updateComputerDiscontinued(computer);
			updateComputerCompanyId(computer);

		}
	}

	private void updateComputerName(Computer computer) throws NothingSelectedException {
		if (computer.getName() != null && !"".equals(computer.getName())) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPUTER_NAME_QUERY)) {
				preparedStatement.setString(1, computer.getName());
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NothingSelectedException("Update Name");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}

	}

	private void updateComputerIntroduced(Computer computer) throws NothingSelectedException {
		if (computer.getIntroduced() != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection
							.prepareStatement(UPDATE_COMPUTER_INTRODUCED_QUERY)) {
				prepareStatmentDate(preparedStatement, computer.getIntroduced(), 1);
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NothingSelectedException("Update Introduced");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
	}

	private void updateComputerDiscontinued(Computer computer) throws NothingSelectedException {
		if (computer.getDiscontinued() != null) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection
							.prepareStatement(UPDATE_COMPUTER_DISCONTINUED_QUERY)) {
				prepareStatmentDate(preparedStatement, computer.getDiscontinued(), 1);
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NothingSelectedException("Update Discontinued");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
	}

	private void updateComputerCompanyId(Computer computer) throws NothingSelectedException {
		if (computer.getCompany() != null && computer.getCompany().getId() > 0) {
			try (Connection connection = dbConnection.getconnection();
					PreparedStatement preparedStatement = connection
							.prepareStatement(UPDATE_COMPUTER_COMPANYID_QUERY)) {
				preparedStatement.setInt(1, computer.getCompany().getId());
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NothingSelectedException("Update Company");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
	}

}
