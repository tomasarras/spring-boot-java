package com.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.constant.ViewConstants;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping
@ApiIgnore
public class ViewController {
	
	@GetMapping
	public String getIndex() {
		return ViewConstants.INDEX_VIEW;
	}
	
	@GetMapping("/registrar")
	public String getRegister() {
		return ViewConstants.REGISTER_VIEW;
	}
	
	@GetMapping("/login")
	public String getLogin(@RequestParam(name="logout", required=false) String logout, Model model) {
		model.addAttribute("logout", logout);
		return ViewConstants.LOGIN_VIEW;
	}
	
	@GetMapping("/productos")
	public String getProducts() {
		return ViewConstants.PRODUCTS_VIEW;
	}
	
	@GetMapping("/admin")
	public String getAdmin() {
		return ViewConstants.ADMIN_VIEW;
	}
}
