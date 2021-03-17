package com.excilys.formationcdb.controller.web;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formationcdb.config.SpringConfig;
import com.excilys.formationcdb.controller.cli.CliComputerController;
import com.excilys.formationcdb.controller.web.session.DashBoardSessionVariable;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.service.ComputerService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringConfig.class })
public class DashBoardControllerTest {

	@Autowired
	private DashBoardController dashBoardController;

	private ComputerService computerServiceMock;

	@Before
	public void before() {
		computerServiceMock = Mockito.mock(ComputerService.class);
		setComputerServiceComportment();
		ReflectionTestUtils.setField(dashBoardController, "computerService", computerServiceMock);
	}

	private void setComputerServiceComportment() {
		Page page = new Page(10, 1, CliComputerController.COMPUTER_TABLE_NAME);
		ArrayList<Optional<Computer>> content = new ArrayList<>();
		content.add(Optional.of(new Computer.ComputerBuilder().build()));
		page.setContent(content);
		Mockito.when(computerServiceMock.getPage(Mockito.anyObject())).thenReturn(page);
		Mockito.when(computerServiceMock.getComputerNumberbyName(Mockito.anyString())).thenReturn(100);
	}

	@Test
	public void getModelAttributesTest() {
		ModelAndView mv = dashBoardController.getDashBoard("1", "apple");
		assertEquals(1, mv.getModel().get(DashBoardController.ATT_PAGE_NB));
		assertEquals("apple", mv.getModel().get(DashBoardController.ATT_COMPUTER_NAME));
		assertEquals(1, mv.getModel().get(DashBoardController.ATT_PAGEINDEX_FROM));
		assertEquals(100, mv.getModel().get(DashBoardController.ATT_COMPUTER_NB));
		assertEquals(Page.PAGEINDEX_SIZE, mv.getModel().get(DashBoardController.ATT_PAGEINDEX_TO));
		assertEquals(1,
				((ArrayList<Optional<Computer>>) mv.getModel().get(DashBoardController.ATT_COMPUTERDTO_LIST)).size());
		assertEquals(DashBoardController.VIEW, mv.getViewName());

	}

	@Test
	public void postSessionAttributesTest() {
		dashBoardController.postDashBoard("10", "1,2,3", "COMPUTER_NAME", "1");
		DashBoardSessionVariable sessionVar = dashBoardController.getSessionVariable();
		assertEquals(10, sessionVar.getPageSize());
		assertEquals(Page.SortAttribute.COMPUTER_NAME, sessionVar.getSortAttribute());
		assertEquals(Page.SortOrder.DESC, sessionVar.getSortOrder());
		dashBoardController.postDashBoard("100", null, "COMPUTER_NAME", "1");
		assertEquals(Page.SortOrder.ASC, sessionVar.getSortOrder());
		assertEquals(100, sessionVar.getPageSize());
	}
}
