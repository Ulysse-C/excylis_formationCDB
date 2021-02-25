package com.excilys.formationCDB.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class ComputerTest {

	@Test
	public void testSetDates() {
		Computer computer = new Computer();
		String date = "25/02/2021";
		computer.setDiscontinued(date);
		assertEquals(computer.getDiscontinued(), LocalDate.of(2021, 2, 25));
		computer.setDiscontinued(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(computer.getDiscontinued(), LocalDate.of(2021, 2, 25));
		computer.setIntroduced(date);
		assertEquals(computer.getIntroduced(), LocalDate.of(2021, 2, 25));
		computer.setIntroduced(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		assertEquals(computer.getIntroduced(), LocalDate.of(2021, 2, 25));

	}

}
