package fr.dawan.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.LoginDto;
import fr.dawan.training.dto.LoginResponseDto;
import fr.dawan.training.services.IUserService;


@RestController
public class LoginController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public LoginResponseDto checkLogin(@RequestBody LoginDto loginDto) throws Exception{
		return userService.checkLogin(loginDto);
	}

}
