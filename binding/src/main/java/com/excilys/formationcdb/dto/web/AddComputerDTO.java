package com.excilys.formationcdb.dto.web;

public class AddComputerDTO {
	
	public String companyId = "";
	public String introducedDate = "";
	public String discontinuedDate = "";
	public String computerName = "";
	
	public String getCompanyId() {
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
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	
}
