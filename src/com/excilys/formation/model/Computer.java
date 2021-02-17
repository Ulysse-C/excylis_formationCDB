package com.excilys.formation.model;

import java.sql.Date;
import java.time.LocalDate;

public class Computer {

	private String name;
	private int id;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	private Integer companyId;
	
	public Computer(String name, int id, int companyId) {
		this.companyId = companyId;
		this.name = name;
		this.id = id;
	}
	
	public Computer(String name, int id, Date introduced, int companyId, Date introduced3, Date discontinued) {
		this(name, id, companyId);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
	}

	public Computer(String name, int id, Date introduced, Date discontinued, Company company, int companyId) {
		this(name, id, introduced, companyId, introduced, discontinued);
		
		this.company = company;
		
		
	}


	private void setDiscontinued(Date discontinued) {
		if (discontinued != null) {
			this.discontinued = discontinued.toLocalDate();
		}
	}

	private void setIntroduced(Date introduced) {
		if (introduced != null ) {
			this.introduced = introduced.toLocalDate();
		}
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Company getConstructor() {
		return company;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name).append(", constructed ");
		if (company != null) {
			stringBuilder.append("by ").append(company.getName()).append(",");
		}
		stringBuilder.append(" from ");
		if (introduced != null) {
			stringBuilder.append(introduced.toString());
		} else {
			stringBuilder.append("///");
		}
		stringBuilder.append(" to ");
		if (discontinued != null) {
			stringBuilder.append(discontinued.toString());
		} else {
			stringBuilder.append("///");
		}
		return stringBuilder.toString();
	}

	public int getCompanyId() {
		return companyId;
	}
}
