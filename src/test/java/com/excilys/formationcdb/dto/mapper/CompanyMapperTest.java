package com.excilys.formationcdb.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import com.excilys.formationcdb.dto.web.AddCompanyDTO;
import com.excilys.formationcdb.dto.web.mapper.WebCompanyMapper;
import com.excilys.formationcdb.model.Company;

public class CompanyMapperTest {

	@Test
	public void testCreateAddCompanyDTO() {
		Company company = new Company.CompanyBuilder().setId(1).setName("name").build();
		AddCompanyDTO companyDTO = WebCompanyMapper.createAddCompanyDTO(Optional.of(company));
		assertEquals("1", companyDTO.getId());
		assertEquals("name", companyDTO.getName());
	}

}
