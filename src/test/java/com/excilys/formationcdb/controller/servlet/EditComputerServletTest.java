package com.excilys.formationcdb.controller.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.excilys.formationcdb.controller.servlet.EditComputerServlet;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class EditComputerServletTest {

	@Test
	public void testDoGet() {
		ServletRunner servletRunner = new ServletRunner();
		servletRunner.registerServlet("formationCDB/editComputer", EditComputerServlet.class.getName());
		ServletUnitClient testServlet = servletRunner.newClient();
		WebRequest request = new GetMethodWebRequest("http://localhost:8080/formationCDB/editComputer");
		WebResponse response;
		try {
			response = testServlet.getResponse(request);
			HttpSession session = testServlet.getSession(true);
			assertNotNull(response);
			assertEquals("text/plain", response.getContentType());
			//assertEquals(ArrayList.class, session.getAttribute("listComputer").getClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
