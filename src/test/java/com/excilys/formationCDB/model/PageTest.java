package com.excilys.formationCDB.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class PageTest {

	@Test
	public void testConstructor() {
		Page page = new Page(1, 1, "string");
		assertEquals(page.getNumber(), 1);
		assertEquals(page.getSize(), 1);
		assertEquals(page.getTable(), "string");
	}

	@Test
	public void testGetSetContent() {
		Page page = new Page(1, 1, "string");
		ArrayList<Integer> list = new ArrayList<Integer>();
		page.setContent(list);
		assertEquals(page.getContent(), list);
	}

	@Test
	public void testGetSetNext() {
		Page page = new Page(1, 1, "string");
		Page nextGenerated = page.getNextPage();
		assertEquals(nextGenerated.getNumber(), 2);
		assertEquals(nextGenerated.getSize(), 1);
		Page next = new Page(1, 1, "string");
		page.setNextPage(next);
		assertEquals(page.getNextPage(), next);
	}
	
	@Test
	public void testGetSetPrevious() {
		Page page = new Page(1, 2, "string");
		Page previousGenerated = page.getPreviousPage();
		assertEquals(previousGenerated.getNumber(), 1);
		assertEquals(previousGenerated.getSize(), 1);
		Page previous = new Page(1, 1, "string");
		page.setPreviousPage(previous);
		assertEquals(page.getPreviousPage(), previous);
		page = new Page(1, 1, "string");
		assertEquals(page.getPreviousPage(), page);
	}
}
