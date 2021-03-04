package com.excilys.formationCDB.dao;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NothingSelectedException;
import com.excilys.formationCDB.model.Computer;

public class DAOComputerTest {

	@Test
	public void testGetComputerById() {
		Computer computer = new Computer.ComputerBuilder().setId(1).setName("MacBook Pro 15.4 inch").build();
		DAOComputer daoComputer = DAOComputer.getInstance();
		assertEquals(daoComputer.getComputerById(1), Optional.of(computer));
	}
	/*
	 * @Test public void deleteComputerByIdTest() { Computer computer = new
	 * Computer.ComputerBuilder().setId(1).build(); DAOComputer daoComputer=
	 * DAOComputer.getInstance(); try { daoComputer.deleteComputerById(computer);
	 * assertEquals(daoComputer.getComputerById(1), Optional.empty());
	 * 
	 * } catch (CustomSQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (NoComputerSelectedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 * 
	 */

}
