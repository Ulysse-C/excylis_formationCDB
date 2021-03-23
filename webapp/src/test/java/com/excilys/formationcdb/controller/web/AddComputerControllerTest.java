// package com.excilys.formationcdb.controller.web;

// import static org.junit.Assert.assertEquals;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.util.ReflectionTestUtils;
// import org.springframework.web.servlet.ModelAndView;

// import com.excilys.formationcdb.config.SpringConfig;
// import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
// import com.excilys.formationcdb.dto.web.AddComputerDTO;
// import com.excilys.formationcdb.model.Company;
// import com.excilys.formationcdb.service.CompanyService;
// import com.excilys.formationcdb.service.ComputerService;

// @RunWith(SpringJUnit4ClassRunner.class)
// @WebAppConfiguration
// @ContextConfiguration(classes = { SpringConfig.class })
// public class AddComputerControllerTest {

// 	@Autowired
// 	private ComputerController ComputerController;

// 	private ComputerService computerServiceMock;
// 	private CompanyService companyServiceMock;
// 	private AddComputerValidator addComputerValidatorMock;

// 	@Before
// 	public void before() {
// 		computerServiceMock = Mockito.mock(ComputerService.class);
// 		addComputerValidatorMock = Mockito.mock(AddComputerValidator.class);
// 		companyServiceMock = Mockito.mock(CompanyService.class);
// 		setCompanyServiceComportment();
// 		setValidatorComportment();
// 		ReflectionTestUtils.setField(ComputerController, "computerService", computerServiceMock);
// 		ReflectionTestUtils.setField(ComputerController, "companyService", companyServiceMock);
// 		ReflectionTestUtils.setField(ComputerController, "addValidator", addComputerValidatorMock);

// 	}

// 	private void setValidatorComportment() {
// 		Map<String, String> content = new HashMap<>();
// 		content.put("test", "test");
// 		Mockito.when(addComputerValidatorMock.validate(Mockito.any())).thenReturn(content);
// 	}

// 	private void setCompanyServiceComportment() {
// 		List<Company> content = new ArrayList<>();
// 		content.add(new Company.CompanyBuilder().build());
// 		Mockito.when(companyServiceMock.getCompanyList()).thenReturn(content);
// 	}

// 	//@Test
// 	public void getModelAttributesTest() {
// 		ModelAndView mv = ComputerController.getAddComputer();
// 		assertEquals(1,
// 				((ArrayList<Company>) mv.getModel().get(ComputerController.ATT_COMPANYLIST)).size());
// 		assertEquals(ComputerController.VIEW_ADD, mv.getViewName());
// 	}

// 	//@Test
// 	public void createComputerTest() {
// 		AddComputerDTO computerDTO = new AddComputerDTO();
// 		computerDTO.setComputerName("name");
// 		ModelAndView mv = ComputerController.postAddComputer(computerDTO);
// 		assertEquals(computerDTO, mv.getModel().get(ComputerController.ATT_COMPUTERDTO));
// 		assertEquals(1, ((Map<String, String>) mv.getModel().get(ComputerController.ATT_ERRORS)).size());
// 	}
// }
