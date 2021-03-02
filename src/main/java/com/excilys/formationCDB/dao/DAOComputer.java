package com.excilys.formationCDB.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.logger.CDBLogger;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public final class DAOComputer {

	private DBConnection dbConnection;
	private final static DAOComputer INSTANCE = new DAOComputer();

	private String getComputerByIdQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id = ?";
	private String getComputerListByNameQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ?";
	private String createComputerQuery = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String updateComputerNameQuery = "UPDATE computer SET name = ? WHERE id = ?";
	private String deleteComputerByIdQuery = "DELETE FROM computer WHERE id = ?";
	private String getPageQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id ORDER BY computer.id LIMIT ? OFFSET ?";
	private String getPageByNameQuery = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ? ORDER BY computer.id LIMIT ? OFFSET ?";
	private String updateComputerNameAndDateQuery = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
	private String getComputerNumberQuery = "SELECT COUNT(*) AS nbComputer FROM computer";
	private String getComputerNumberByNameQuery = "SELECT COUNT(name) AS nbComputer FROM computer WHERE computer.name LIKE ?";

	private DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}

	public final static DAOComputer getInstance() {
		return INSTANCE;
	}

	public Optional<Computer> getComputerById(int id) throws CustomSQLException {
		Optional<Computer> computer = Optional.empty();
		try (PreparedStatement preparedStatement = dbConnection.getconnection()
				.prepareStatement(getComputerByIdQuery)) {
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
			try (PreparedStatement preparedStatement = dbConnection.getconnection()
					.prepareStatement(createComputerQuery, Statement.RETURN_GENERATED_KEYS)) {
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

	public void updateComputerName(Computer computer) throws NoComputerSelectedException {
		if (computer != null) {
			try (PreparedStatement preparedStatement = dbConnection.getconnection()
					.prepareStatement(updateComputerNameQuery)) {
				preparedStatement.setNString(1, computer.getName());
				preparedStatement.setInt(2, computer.getId());
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NoComputerSelectedException("Update Name");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
	}

	public void deleteComputerById(int id) throws NoComputerSelectedException {
		try (PreparedStatement preparedStatement = dbConnection.getconnection()
				.prepareStatement(deleteComputerByIdQuery)) {
			preparedStatement.setInt(1, id);
			int row = preparedStatement.executeUpdate();
			if (row == 0) {
				throw new NoComputerSelectedException("Delete");
			}
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
	}

	public void updateComputerNameAndDate(Computer computer) throws NoComputerSelectedException {
		if (computer != null) {
			try (PreparedStatement preparedStatement = dbConnection.getconnection()
					.prepareStatement(updateComputerNameAndDateQuery)) {
				prepareStatmentUpdateNameDate(preparedStatement, computer);
				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					throw new NoComputerSelectedException("Update name and date");
				}
			} catch (SQLException sqlException) {
				CDBLogger.logError(sqlException);
			}
		}
	}

	private void prepareStatmentUpdateNameDate(PreparedStatement preparedStatement, Computer computer)
			throws SQLException {
		preparedStatement.setNString(1, computer.getName());
		prepareStatmentDate(preparedStatement, computer.getIntroduced(), 2);
		prepareStatmentDate(preparedStatement, computer.getDiscontinued(), 3);
		preparedStatement.setInt(4, computer.getId());

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

	public List<Optional<Computer>> getComputerListByName(String computerName) {
		List<Optional<Computer>> computerList = new ArrayList<Optional<Computer>>();
		try (PreparedStatement preparedStatement = dbConnection.getconnection()
				.prepareStatement(getComputerListByNameQuery)) {
			preparedStatement.setString(1, "%" + computerName + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			computerList = convertToListComputerList(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return computerList;
	}

	public int getComputerNumber() {
		int number = 0;
		try (PreparedStatement preparedStatement = dbConnection.getconnection()
				.prepareStatement(getComputerNumberQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			number = getComputerNumberFromResultSet(resultSet);
		} catch (SQLException sqlException) {
			CDBLogger.logError(sqlException);
		}
		return number;
	}

	public int getComputerNumberbyName(String search) {
		int number = 0;
		try (PreparedStatement preparedStatement = dbConnection.getconnection()
				.prepareStatement(getComputerNumberByNameQuery)) {
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
			try (PreparedStatement preparedStatement = dbConnection.getconnection().prepareStatement(getPageQuery)) {
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
			try (PreparedStatement preparedStatement = dbConnection.getconnection()
					.prepareStatement(getPageByNameQuery)) {
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

}
