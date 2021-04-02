package com.excilys.formationcdb.dto.dao.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.formationcdb.dto.dao.CompanyPersist;
import com.excilys.formationcdb.dto.dao.ComputerPersist;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page.SortOrder;
import com.querydsl.core.types.Order;

public class DaoCompanyMapper extends DaoMapper {
	public static Company toCompany(CompanyPersist companyPersist) {
		Company company = new Company.CompanyBuilder().setId(companyPersist.getId()).setName(companyPersist.getName())
				.build();
		return company;
	}

	public static List<Company> toCompanyList(List<CompanyPersist> companyPersistList) {
		return companyPersistList.stream().map(DaoCompanyMapper::toCompany).collect(Collectors.toList());
	}
	
	public static CompanyPersist toCompanyPersist(Company company) {
		CompanyPersist companyPersist = new CompanyPersist();
		companyPersist.setId(company.getId());
		companyPersist.setName(company.getName());
		return companyPersist;
	}

}
