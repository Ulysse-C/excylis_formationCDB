//package com.excilys.formationcdb.model;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//public class PageTest {
//
//	@Test
//	public void testConstructor() {
//		Page page = new Page(1, 1);
//		assertEquals(page.getNumber(), 1);
//		assertEquals(page.getSize(), 1);
//	}
//
//	@Test
//	public void testGetSetContent() {
//		Page page = new Page(1, 1);
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		page.setContent(list);
//		assertEquals(page.getContent(), list);
//	}
//
//	@Test
//	public void testGetSetNext() {
//		Page page = new Page(1, 1);
//		Page nextGenerated = page.getNextPage();
//		assertEquals(nextGenerated.getNumber(), 2);
//		assertEquals(nextGenerated.getSize(), 1);
//		Page next = new Page(1, 1);
//		page.setNextPage(next);
//		assertEquals(page.getNextPage(), next);
//	}
//
//	@Test
//	public void testGetSetPrevious() {
//		Page page = new Page(1, 2);
//		Page previousGenerated = page.getPreviousPage();
//		assertEquals(previousGenerated.getNumber(), 1);
//		assertEquals(previousGenerated.getSize(), 1);
//		Page previous = new Page(1, 1);
//		page.setPreviousPage(previous);
//		assertEquals(page.getPreviousPage(), previous);
//		page = new Page(1, 1);
//		assertEquals(page.getPreviousPage(), page);
//	}
//
//	@Test
//	public void testPageIndexFrom() {
//		Page page = new Page(10, 1);
//		assertEquals(1, page.getPageIndexFrom());
//		page = new Page(10, 2);
//		assertEquals(true, Page.PAGEINDEX_BEFORE_CURRENT_PAGE - 5 <= page.getPageIndexFrom());
//		page = new Page(10, 10);
//		assertEquals(true, Page.PAGEINDEX_BEFORE_CURRENT_PAGE - 5 <= page.getPageIndexFrom());
//	}
//
//	@Test
//	public void testPageIndexTo() {
//		Page page = new Page(10, 1);
//		assertEquals(Page.PAGEINDEX_SIZE, page.getPageIndexTo(500));
//		page = new Page(10, 2);
//		assertEquals(2, page.getPageIndexTo(19));
//		page = new Page(10, 10);
//		assertEquals(true, 10 + Page.PAGEINDEX_SIZE - Page.PAGEINDEX_BEFORE_CURRENT_PAGE >= page.getPageIndexTo(500));
//	}
//}
