package com.excilys.formationCDB.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.excilys.formationCDB.exception.InvalidInputCLIHandlerException;

public class Computer {

	private String name;
	private int id;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	private int companyId;

	public Computer() {

	}

	public Computer(String name, int id, int companyId) {
		this.companyId = companyId;
		this.name = name;
		this.id = id;
	}

	public Computer(String name, int id, int companyId, Date introduced, Date discontinued) {
		this(name, id, companyId);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
	}

	public Computer(String name, int id, Date introduced, Date discontinued, Company company, int companyId) {
		this(name, id, companyId, introduced, discontinued);
		this.company = company;
	}

	private void setDiscontinued(Date discontinued) {
		if (discontinued != null) {
			this.discontinued = discontinued.toLocalDate();
		}
	}

	private void setIntroduced(Date introduced) {
		if (introduced != null) {
			this.introduced = introduced.toLocalDate();
		}
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public int getCompanyId() {
		return companyId;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name).append(", constructed ");
		if (company != null) {
			stringBuilder.append("by ").append(company.getName()).append(",");
		}
		stringBuilder.append(" from ");
		stringBuilder.append(localDateToString(introduced));
		stringBuilder.append(" to ");
		stringBuilder.append(localDateToString(discontinued));
		return stringBuilder.toString();
	}

	private String localDateToString(LocalDate localDate) {
		if (localDate == null) {
			return "///";
		} else {
			return localDate.toString();
		}
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setDiscontinued(String discontinued) throws InvalidInputCLIHandlerException {
		setDiscontinued(discontinued, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDiscontinued(String discontinued, DateTimeFormatter formatter) throws InvalidInputCLIHandlerException {
		if (!discontinued.equals("null")) {
			try {
				this.discontinued = LocalDate.parse(discontinued, formatter);
			} catch (DateTimeParseException dateTimeException) {
				throw new InvalidInputCLIHandlerException("Invalid date format");
			}
		}
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) throws InvalidInputCLIHandlerException {
		setIntroduced(introduced, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	}
	
	public void setIntroduced(String introduced, DateTimeFormatter formatter) throws InvalidInputCLIHandlerException {
		if (!introduced.equals("null")) {
			try {
				this.introduced = LocalDate.parse(introduced, formatter);
			} catch (DateTimeParseException dateTimeException) {
				throw new InvalidInputCLIHandlerException("Invalid date format");
			}
		}
	}

	public void setName(String name) {
		this.name = name;

	}

	public void setCompanyID(String companyID) {
		this.companyId = Integer.parseInt(companyID);
	}

}
