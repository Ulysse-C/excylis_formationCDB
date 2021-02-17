package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class DAOComputer {

	private DBConnection dbConnection;
	private static DAOComputer daoComputer;
	private DAOCompany daoCompany;

	private String listComputerQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer";
	private String getComputerByIdQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	private String getComputerByNameQuery = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	private String createComputerQuery = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private String updateComputerNameQuery = "UPDATE computer SET name = ? WHERE id = ?";
	private String deleteComputerByIdQuery = "DELETE FROM computer WHERE id = ?";

	private DAOComputer() {
		dbConnection = DBConnection.getDaoConnection();
	}

	public static DAOComputer getDaoComputer() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	public void setDaoCompany(DAOCompany daoCompany) {
		this.daoCompany = daoCompany;
	}

	// TO DO REFACTOR
	public List<Computer> listComputer() {
		ArrayList<Computer> computerList = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(listComputerQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			computerList = convertToListComputerList(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	public Computer getComputerById(int id) {
		Computer computer = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getComputerByIdQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			computer = getSingleComputerFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public Computer getComputerByName(String computerName) {
		Computer computer = null;
		try (Connection connection = dbConnection.getconnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(getComputerByNameQuery);
			preparedStatement.setNString(1, computerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			computer = getSingleComputerFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	private ArrayList<Computer> convertToListComputerList(ResultSet resultSetComputerList) {
		ArrayList<Computer> computerList = new ArrayList<>();
		try {
			while (resultSetComputerList.next()) {
				computerList.add(convertToComputer(resultSetComputerList));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	private Computer convertToComputer(ResultSet resultSetComputer) {
		Computer computer = null;
		try {
			Company company = null;
			if (daoCompany != null) {
				company = daoCompany.getCompanyById(resultSetComputer.getInt("company_id"));
			}

			computer = new Computer(resultSetComputer.getNString("name"), resultSetComputer.getInt("id"),
					resultSetComputer.getDate("introduced"), resultSetComputer.getDate("discontinued"), company,
					resultSetComputer.getInt("company_id"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	public Computer getSingleComputerFromResultSet(ResultSet resultSet) {
		Computer computer = null;
		if (resultSet != null) {
			try {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();
					computer = convertToComputer(resultSet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return computer;
	}

	public boolean createComputer(Computer computer) {
		boolean isCreated = false;
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(createComputerQuery, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setNString(1, computer.getName());
				if ( computer.getIntroduced() != null) {
					preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
				} else {
					preparedStatement.setNull(2, Types.DATE);
				}
				if ( computer.getDiscontinued() != null) {
					preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
				} else {
					preparedStatement.setNull(3, Types.DATE);
				}
				preparedStatement.setInt(4, computer.getCompanyId());
				preparedStatement.executeUpdate();
				/*
				if(returnLastInsertId) {
					   ResultSet rs = preparedStatement.getGeneratedKeys();
					    rs.next();
					   int auto_id = rs.getInt(1);
					}
				*/
				isCreated = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isCreated;
	}

	public boolean updateComputerName(Computer computer) {
		boolean isUpdated = false;
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(updateComputerNameQuery);
				preparedStatement.setNString(1, computer.getName());
				preparedStatement.setInt(2, computer.getId());
				preparedStatement.executeUpdate();
				isUpdated = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isUpdated;
	}

	public boolean deleteComputerById(Computer computer) {
		boolean isDeleted = false;
		if (computer != null) {
			try (Connection connection = dbConnection.getconnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(deleteComputerByIdQuery);
				preparedStatement.setInt(1, computer.getId());
				preparedStatement.executeUpdate();
				isDeleted = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isDeleted;
	}

}
