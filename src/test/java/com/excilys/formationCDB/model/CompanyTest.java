package com.excilys.formationCDB.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void testCompany() {
		Company company = new Company("name", 1);
		assertEquals(company.getId(), 1);
		assertEquals(company.getName(), "name");
	}

}
