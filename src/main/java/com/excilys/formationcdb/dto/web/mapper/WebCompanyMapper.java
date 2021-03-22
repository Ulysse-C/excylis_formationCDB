package com.excilys.formationcdb.dto.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.dto.web.AddCompanyDTO;
import com.excilys.formationcdb.model.Company;

public class WebCompanyMapper {

	public static AddCompanyDTO createAddCompanyDTO(Company company) {
		AddCompanyDTO companyDTO = new AddCompanyDTO();
		companyDTO.id = String.valueOf(company.getId());
		companyDTO.name = company.getName();

		return companyDTO;
	}

	public static List<AddCompanyDTO> createAddCompanyDTOList(List<Company> companyList) {
		ArrayList<AddCompanyDTO> list = new ArrayList<>();
		for (Company company : companyList) {
			list.add(createAddCompanyDTO(company));
		}

		return list;
	}
}
