package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.LoginDto;
import fr.dawan.training.dto.LoginResponseDto;
import fr.dawan.training.dto.UserDto;
import fr.dawan.training.entities.User;
import fr.dawan.training.exceptions.AuthentificationException;
import fr.dawan.training.repositories.UserRepository;
import fr.dawan.training.tools.DtoTool;
import fr.dawan.training.tools.JwtTokenTool;
import fr.dawan.training.tools.TokenSaver;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtTokenTool jwtTokenTool;

	@Override
	public List<UserDto> getAll() throws Exception {
		
		List<UserDto> result = new ArrayList<>();
		
		List<User> users = userRepository.findAll();
		for (User user : users) {
			result.add(DtoTool.convert(user, UserDto.class));
		}
		
		return result;
	}

	@Override
	public UserDto findByEmail(String email) throws Exception {
		
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return DtoTool.convert(user, UserDto.class);
		}
		
		return null;
	}

	@Override
	public UserDto saveOrUpdate(UserDto dto) throws Exception {
		
		User user = DtoTool.convert(dto, User.class);
		
		//Si ajout id = 0
		if(user.getId() == 0) {
			user.setPassword(encoder.encode(user.getPassword()));
		}else {
			//Si modif -
			//Vérifier si password modifié ou pas côte client
			//Récupérer le password en Base de données (crypté) et le comparer au password du dto
			User userDB = userRepository.findById(dto.getId()).get();
			
			if(!userDB.getPassword().equals(dto.getPassword())) {
				user.setPassword(encoder.encode(user.getPassword()));
			}
		}
		
		User savedUser = userRepository.saveAndFlush(user);
		
		return DtoTool.convert(savedUser, UserDto.class);
	}

	@Override
	public void deleteById(int id) throws Exception {
		userRepository.deleteById(id);
		
	}

	@Override
	public UserDto getById(int id) throws Exception {
		Optional<User> optional = userRepository.findById(id);
		if(optional.isPresent()) {
			return DtoTool.convert(optional.get(), UserDto.class);
		}
		return null;
	}

	@Override
	public LoginResponseDto checkLogin(LoginDto loginDto) throws Exception {
		
		User user = userRepository.findByEmail(loginDto.getEmail());
		
		if(user != null && encoder.matches(loginDto.getPassword(), user.getPassword())) {
			//Connexion OK - renvoyer le LoginResponseDto
			
			LoginResponseDto responseDto = DtoTool.convert(user, LoginResponseDto.class);
			
			//Gestion du Token
			//Choisir les infos à injecter dans le Token (Claims)
			
			Map<String, Object> claims = new HashMap<>();
			claims.put("userId", user.getId());
			claims.put("fullname", user.getFirstName()+" "+user.getLastName());
			
			//Générer le Token
			String token = jwtTokenTool.doGenerateToken(claims, user.getEmail());
			
			//Sauvegarde du token côté server
			TokenSaver.tokenByEmail.put(user.getEmail(), token);
			
			//Injecter le token dans responseDto
			responseDto.setToken(token);
			
			return responseDto;
		}
		
		
		throw new AuthentificationException("Echec exception.........");
	}

}
