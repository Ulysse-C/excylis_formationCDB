package com.excilys.formationCDB.dto;

public class ComputerCompanyNameDTO {
	public ComputerCompanyNameDTO(String companyName, String introducedDate, String discontinuedDate,
			String computerName) {
		super();
		this.companyName = companyName;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.computerName = computerName;
	}
	private String companyName;
	private String introducedDate;
	private String discontinuedDate;
	private String computerName;
	
	public String getCompanyName() {
		return companyName;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public String getComputerName() {
		return computerName;
	}
}