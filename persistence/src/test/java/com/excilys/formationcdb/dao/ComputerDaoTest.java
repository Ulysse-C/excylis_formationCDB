package com.excilys.formationcdb.dao;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formationcdb.config.DaoConfig;
import com.excilys.formationcdb.model.Computer;

//@SpringBatchTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = { ComputerDao.class, SpringConfig.class })
//public class ComputerDaoTest {
//
//	@Autowired
//	ComputerDao daoComputer;
//
//	@Test
//	public void testGetComputerById() {
//		Computer computer = new Computer.ComputerBuilder().setId(1).setName("MacBook Pro 15.4 inch").build();
//		assertEquals(Optional.of(computer), daoComputer.getComputerById(1));
//	}
////
////	@Test
////	public void testDeleteComputerById() {
////		DaoComputer daoComputer = DaoComputer.getInstance();
////		try {
////			daoComputer.deleteComputerById(1);
////		} catch (NothingSelectedException e) {
////			e.printStackTrace();
////		}
////		assertEquals(daoComputer.getComputerById(1), Optional.empty());
////	}
//}
