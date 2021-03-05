package com.excilys.formationcdb.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.dto.AddCompanyDTO;
import com.excilys.formationcdb.model.Company;

public class CompanyMapper {

	public static AddCompanyDTO createAddCompanyDTO(Optional<Company>company) {
		AddCompanyDTO companyDTO = new AddCompanyDTO();
		if (company.isPresent()) {
			companyDTO.id = String.valueOf(company.get().getId());
			companyDTO.name = company.get().getName();	
		}
		return companyDTO;
	}

	public static List<AddCompanyDTO> createAddCompanyDTOList(List<Optional<Company>> companyList) {
		ArrayList<AddCompanyDTO> list = new ArrayList<>();
		for (Optional<Company> company : companyList ) {
			list.add(createAddCompanyDTO(company));
		}
				
		return list;
	}
}
