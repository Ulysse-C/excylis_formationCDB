package com.excilys.formationcdb.dto.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class CompanyPersist {
	
	private int id;
	private String name;
	
	private Set<ComputerPersist> computerList = new HashSet<>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "company")
	public Set<ComputerPersist> getComputerList() {
		return new HashSet<>(computerList);
	}

	public void setComputerList(Set<ComputerPersist> computerList) {
		this.computerList = computerList;
	}

	public void removeComputer(ComputerPersist computerPersist) {
		if (!computerList.contains(computerPersist)) {
			return;
		}
		computerList.remove(computerPersist);
		computerPersist.setCompany(null);
	}

	public void addComputer(ComputerPersist computerPersist) {
		if (computerList.contains(computerPersist)) {
			return;
		}
		computerList.add(computerPersist);
		computerPersist.setCompany(this);
	}
}
