package com.excilys.formationcdb.controller.web;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.service.CompanyService;
import com.excilys.formationcdb.service.ComputerService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringConfig.class })
public class AddComputerControllerTest {

	@Autowired
	private AddComputerController addComputerController;

	private ComputerService computerServiceMock;
	private CompanyService companyServiceMock;
	private AddComputerValidator addComputerValidatorMock;

	@Before
	public void before() {
		computerServiceMock = Mockito.mock(ComputerService.class);
		addComputerValidatorMock = Mockito.mock(AddComputerValidator.class);
		companyServiceMock = Mockito.mock(CompanyService.class);
		setCompanyServiceComportment();
		setValidatorComportment();
		ReflectionTestUtils.setField(addComputerController, "computerService", computerServiceMock);
		ReflectionTestUtils.setField(addComputerController, "companyService", companyServiceMock);
		ReflectionTestUtils.setField(addComputerController, "validator", addComputerValidatorMock);

	}

	private void setValidatorComportment() {
		Map<String, String> content = new HashMap<>();
		content.put("test", "test");
		Mockito.when(addComputerValidatorMock.validate(Mockito.any())).thenReturn(content);
	}

	private void setCompanyServiceComportment() {
		ArrayList<Optional<Company>> content = new ArrayList<>();
		content.add(Optional.of(new Company.CompanyBuilder().build()));
		Mockito.when(companyServiceMock.getCompanyList()).thenReturn(content);
	}

	@Test
	public void getModelAttributesTest() {
		ModelAndView mv = addComputerController.getAddComputer();
		assertEquals(1,
				((ArrayList<Optional<Company>>) mv.getModel().get(AddComputerController.ATT_COMPANYLIST)).size());
		assertEquals(AddComputerController.VIEW, mv.getViewName());
	}

	@Test
	public void createComputerTest() {
		AddComputerDTO computerDTO = new AddComputerDTO();
		computerDTO.setComputerName("name");
		ModelAndView mv = addComputerController.postAddComputer(computerDTO);
		assertEquals(computerDTO, mv.getModel().get(AddComputerController.ATT_COMPUTERDTO));
		assertEquals(1, ((Map<String, String>) mv.getModel().get(AddComputerController.ATT_ERRORS)).size());
	}
}
