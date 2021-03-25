package com.excilys.formationcdb.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/login")
	public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		model.addAttribute("errorMessge", errorMessge);
		return modelAndView;
	}

	@GetMapping(value = "/logout")
	public RedirectView logoutPage(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes attributes) {
		RedirectView redirectView = new RedirectView("login");
		attributes.addAttribute("logout", true);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return redirectView;
	}
	
//	 @PostMapping("/register")
//	    public String doRegister() {
//	        String encodedPassword  = passwordEncoder.encode("123456");
//
//	        User user = new User();
//	        user.setEnabled(Boolean.TRUE);
//	        user.setPassword(encodedPassword);
//	        user.setUsername(userDto.getUsername());
//
//	        UserAuthority boardAuthority = new UserAuthority();
//	        boardAuthority.setAuthority("BOARD");
//	        boardAuthority.setUser(user);
//	        user.getAuthorities().add(boardAuthority);
//	        userRepository.save(user);
//
//	        return "register-success";
//	    }
}