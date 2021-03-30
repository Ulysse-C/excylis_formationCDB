package com.excilys.formationcdb.dto.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.dto.web.WebCompanyDTO;
import com.excilys.formationcdb.model.Company;

public class WebCompanyMapper {

	public static WebCompanyDTO createAddCompanyDTO(Company company) {
		WebCompanyDTO companyDTO = new WebCompanyDTO();
		companyDTO.id = String.valueOf(company.getId());
		companyDTO.name = company.getName();

		return companyDTO;
	}

	public static List<WebCompanyDTO> createAddCompanyDTOList(List<Company> companyList) {
		ArrayList<WebCompanyDTO> list = new ArrayList<>();
		for (Company company : companyList) {
			list.add(createAddCompanyDTO(company));
		}

		return list;
	}
}
