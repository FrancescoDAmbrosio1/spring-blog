package it.springBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class SecutiryController {
	
	@GetMapping("/login")
	private String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	private String logout() {
		return "logout";
	}

}
