package com.excilys.formationcdb.dto.dao;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerPersist {

	
	private int id;

	private String name = "";

	private LocalDate introduced;
	private LocalDate discontinued;
	private int companyId;
	
	private CompanyPersist company;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ComputerPersist [id=" + id + ", name=" + name + "]";
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	
	@ManyToOne(cascade = CascadeType.MERGE,fetch= FetchType.EAGER)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	public CompanyPersist getCompany() {
		return company;
	}

	public void setCompany(CompanyPersist company) {
		if (sameAsFormer(company))
			return;
		CompanyPersist oldCompany = this.company;
		this.company = company;
		if (oldCompany != null) {
			oldCompany.removeComputer(this);
		}
		if (company != null) {
			company.addComputer(this);
		}
		this.company = company;
	}

	private boolean sameAsFormer(CompanyPersist newCompany) {
		return company == null ? newCompany == null : company.equals(newCompany);
	}
	
}
