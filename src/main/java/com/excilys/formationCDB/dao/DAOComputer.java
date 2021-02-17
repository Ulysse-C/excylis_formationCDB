package com.excilys.formationCDB.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public final class DAOComputer {

	private DBConnection dbConnection;
	private final static DAOComputer INSTANCE = new DAOComputer();

	private String listComputerQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer INNER JOIN company ON company.id = computer.company_id ORDER BY computer.id;";
	private String getComputerByIdQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	private String getComputerByNameQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	private String createComputerQuery = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String updateComputerNameQuery = "UPDATE computer SET name = ? WHERE id = ?";
	private String deleteComputerByIdQuery = "DELETE FROM computer WHERE id = ?";
	private String getPageQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer INNER JOIN company ON company.id = computer.company_id ORDER BY computer.id LIMIT ? OFFSET ?";

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

	public void createComputer(Computer computer) throws CustomSQLException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(createComputerQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setNString(1, computer.getName());
				if (computer.getIntroduced() != null) {
					preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
				} else {
					preparedStatement.setNull(2, Types.DATE);
				}
				if (computer.getDiscontinued() != null) {
					preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
				} else {
					preparedStatement.setNull(3, Types.DATE);
				}
				preparedStatement.setInt(4, computer.getCompanyId());
				preparedStatement.executeUpdate();
				/*
				 * if(returnLastInsertId) { ResultSet rs = preparedStatement.getGeneratedKeys();
				 * rs.next(); int auto_id = rs.getInt(1); }
				 */
			} catch (SQLException sqlException) {
				if (sqlException.getErrorCode() == 1452) {
					throw new CustomSQLException("This company key is not valid");
				}
				System.out.println(sqlException.getErrorCode());
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	public void updateComputerName(Computer computer) throws CustomSQLException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(updateComputerNameQuery);
				preparedStatement.setNString(1, computer.getName());
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new CustomSQLException("No computer selected");
				}
			} catch (SQLException sqlException) {
				throw new CustomSQLException(sqlException.getMessage());
			}
		}
	}

	public void deleteComputerById(Computer computer) throws CustomSQLException {
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(deleteComputerByIdQuery);
				preparedStatement.setInt(1, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new CustomSQLException("No computer selected");
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

}
