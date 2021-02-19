package com.excilys.formationCDB.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public final class DAOComputer {

	private DBConnection dbConnection;
	private final static DAOComputer INSTANCE = new DAOComputer();

	private String getComputerByIdQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer INNER JOIN company ON company.id = computer.company_id WHERE computer.id = ?";
	private String getComputerByNameQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer INNER JOIN company ON company.id = computer.company_id WHERE computer.name = ?";
	private String createComputerQuery = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String updateComputerNameQuery = "UPDATE computer SET name = ? WHERE id = ?";
	private String deleteComputerByIdQuery = "DELETE FROM computer WHERE id = ?";
	private String getPageQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer INNER JOIN company ON company.id = computer.company_id ORDER BY computer.id LIMIT ? OFFSET ?";
	private String updateComputerNameAndDateQuery = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";

	private DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}

	public final static DAOComputer getInstance() {
		return INSTANCE;
	}

	public Computer getComputerById(int id) throws CustomSQLException {
		Computer computer = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getComputerByIdQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			computer = getSingleComputerFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			throw new CustomSQLException(sqlException.getMessage());
		}
		return computer;
	}

	public Computer getComputerByName(String computerName) throws CustomSQLException {
		Computer computer = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getComputerByNameQuery);
			preparedStatement.setNString(1, computerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			computer = getSingleComputerFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			throw new CustomSQLException(sqlException.getMessage());
		}
		return computer;
	}

	private ArrayList<Computer> convertToListComputerList(ResultSet resultSetComputerList) throws SQLException {
		ArrayList<Computer> computerList = new ArrayList<>();
		while (resultSetComputerList.next()) {
			computerList.add(convertToComputer(resultSetComputerList));
		}

		return computerList;
	}

	private Computer convertToComputer(ResultSet resultSetComputer) throws SQLException {
		Computer computer = null;
		Company company = new Company(resultSetComputer.getNString("company.name"),
				resultSetComputer.getInt("computer.company_id"));
		computer = new Computer(resultSetComputer.getNString("computer.name"), resultSetComputer.getInt("computer.id"),
				resultSetComputer.getDate("computer.introduced"), resultSetComputer.getDate("computer.discontinued"),
				company, resultSetComputer.getInt("computer.company_id"));
		return computer;
	}

	public Computer getSingleComputerFromResultSet(ResultSet resultSet) throws SQLException {
		Computer computer = null;
		if (resultSet != null) {
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				computer = convertToComputer(resultSet);
			}

		}
		return computer;
	}

	public void createComputer(Computer computer) throws CustomSQLException, CompanyKeyInvalidException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(createComputerQuery,
						Statement.RETURN_GENERATED_KEYS);
				prepareStatmentCreate(preparedStatement, computer);
				preparedStatement.executeUpdate();
			} catch (SQLException sqlException) {
				if (sqlException.getErrorCode() == 1452) {
					throw new CompanyKeyInvalidException();
				}
				System.out.println(sqlException.getErrorCode());
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	private void prepareStatmentCreate(PreparedStatement preparedStatement, Computer computer) throws SQLException {
		preparedStatement.setNString(1, computer.getName());
		prepareStatmentDate(preparedStatement, computer.getIntroduced(), 2);
		prepareStatmentDate(preparedStatement, computer.getDiscontinued(), 3);
		preparedStatement.setInt(4, computer.getCompanyId());
	}

	private void prepareStatmentDate(PreparedStatement preparedStatement, LocalDate localdate, int index) throws SQLException {
		if (localdate != null) {
			preparedStatement.setDate(index, Date.valueOf(localdate));
		} else {
			preparedStatement.setNull(index, Types.DATE);
		}
	}

	public void updateComputerName(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(updateComputerNameQuery);
				preparedStatement.setNString(1, computer.getName());
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NoComputerSelectedException();
				}
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	public void deleteComputerById(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(deleteComputerByIdQuery);
				preparedStatement.setInt(1, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NoComputerSelectedException();
				}
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	public Page<Computer> getPage(Page<Computer> page) throws CustomSQLException {
		if (page != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(getPageQuery);
				preparedStatement.setInt(1, page.getSize());
				preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
				ResultSet resultSet = preparedStatement.executeQuery();
				page.setContent(convertToListComputerList(resultSet));
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
		return page;
	}

	public void updateComputerNameAndDate(Computer computer) throws NoComputerSelectedException, CustomSQLException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(updateComputerNameAndDateQuery );
				prepareStatmentUpdateNameDate(preparedStatement, computer);
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NoComputerSelectedException();
				}
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	private void prepareStatmentUpdateNameDate(PreparedStatement preparedStatement, Computer computer) throws SQLException {
		preparedStatement.setNString(1, computer.getName());
		prepareStatmentDate(preparedStatement, computer.getIntroduced(), 2);
		prepareStatmentDate(preparedStatement, computer.getDiscontinued(), 3);
		preparedStatement.setInt(4, computer.getId());

	}

}
