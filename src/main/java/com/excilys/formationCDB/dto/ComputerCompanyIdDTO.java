package com.excilys.formationCDB.dto;

public class ComputerCompanyIdDTO {
	
	private int companyId;
	private String introducedDate;
	private String discontinuedDate;
	private String computerName;
	
	public ComputerCompanyIdDTO(int companyId, String introducedDate, String discontinuedDate,
			String computerName) {
		super();
		this.companyId = companyId;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.computerName = computerName;
	}
	
	public int getCompanyName() {
		return companyId;
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
