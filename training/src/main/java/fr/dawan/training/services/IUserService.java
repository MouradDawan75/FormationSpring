package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.LoginDto;
import fr.dawan.training.dto.LoginResponseDto;
import fr.dawan.training.dto.UserDto;
import fr.dawan.training.entities.User;

public interface IUserService {
	
	List<UserDto> getAll() throws Exception;
	UserDto findByEmail(String email) throws Exception;
	UserDto saveOrUpdate(UserDto dto) throws Exception;
	void deleteById(int id) throws Exception;
	UserDto getById(int id) throws Exception;
	LoginResponseDto checkLogin(LoginDto loginDto) throws Exception;

}
