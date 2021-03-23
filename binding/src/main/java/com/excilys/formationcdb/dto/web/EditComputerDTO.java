package com.excilys.formationcdb.dto.web;

public class EditComputerDTO {
	public String companyName = "";
	public String computerName = "";
	public String companyId = "";
	public String computerId = "";
	public String introducedDate = "";
	public String discontinuedDate = "";
	
	public String getCompanyName() {
		return companyName;
	}
	public String getComputerName() {
		return computerName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getComputerId() {
		return computerId;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setComputerId(String computerId) {
		this.computerId = computerId;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
}
