package com.excilys.formationcdb.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.excilys.formationcdb.config.DaoConfig;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DaoConfig.class }, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = { "getDataSource" })
public class ComputerDaoTest {

	@Autowired
	private ComputerDao computerDao;

	@Test
	@DatabaseSetup("classpath:data.xml")
	public void getByIdTest() throws Exception {
		Optional<Computer> computer = computerDao.getComputerById(1);
		assertEquals("computer_1_a", computer.orElse(new Computer.ComputerBuilder().build()).getName());
	}

	@Test
	@DatabaseSetup("classpath:data.xml")
	public void getComputerNumberbyNameTest() throws Exception {
		int computerNumber = computerDao.getComputerNumberbyName("");
		assertEquals(4, computerNumber);
		computerNumber = computerDao.getComputerNumberbyName("a");
		assertEquals(2, computerNumber);
	}
	
	@Test
	@DatabaseSetup("classpath:data.xml")
	@ExpectedDatabase("classpath:data_exp.xml")
	public void createTest() throws Exception {
		Company company = new Company.CompanyBuilder().setId(1).setName("company_1").build();
		Computer computer = new Computer.ComputerBuilder().setCompany(company).setName("test").setIntroduced(LocalDate.of(2000, 10, 10)).setDiscontinued(LocalDate.of(2000, 11, 11)).build();
		computerDao.createComputer(computer);
	}
}
