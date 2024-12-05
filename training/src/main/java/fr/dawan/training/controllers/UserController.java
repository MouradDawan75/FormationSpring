package fr.dawan.training.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.dto.UserDto;
import fr.dawan.training.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	
	@Value("${storage.folder}")
	private String storageFolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//Liste 
	
	@GetMapping(produces = "application/json")
	public List<UserDto> getAllBy() throws Exception{
		
		return userService.getAll();
		
	}
	
	//Get By Id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getbyId(@PathVariable("id") int id) throws Exception{
		UserDto dto = userService.getById(id);
		if(dto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = "+id+" not found.");
		}
	}
	

	
	//Delete product
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) throws Exception{
		UserDto dto = userService.getById(id);
		if(dto != null) {
			userService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("User with id = "+id+" deleted.");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = "+id+" not found.");
		}
	}
	
	//Save or update product
	
	@PostMapping(value={"/save", "/update"}, produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<UserDto> saveOrUpdate(@RequestParam("userDtoStr") String userDtoStr, 
			@RequestParam("file") MultipartFile file) throws Exception{
		
		//Gestion du productDtoStr -> convertir en Objet
		UserDto dto = objectMapper.readValue(userDtoStr, UserDto.class);
		
		//Construction du path + personnaliser le nom du fichier
		String filePath = "/"+dto.getEmail()+"-"+file.getOriginalFilename();
		File f = new File(storageFolder+filePath);
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
		bos.write(file.getBytes());
		
		dto.setImagePath(filePath);
		
		UserDto savedDto = userService.saveOrUpdate(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
				
		
	}
	
	//Get product image by id
	
	@GetMapping(value="/image/{userId}", produces = "application/octet-stream")
	public ResponseEntity<Resource> getProductImage(@PathVariable("userId") int id) throws Exception{
		
		//Appel de getById pour récupérer le produit depuis la BD
		UserDto dto = userService.getById(id);
		
		Path path = Paths.get(".").resolve(storageFolder+dto.getImagePath());
		Resource resource = new UrlResource(path.toUri());
		
		Path newPath = resource.getFile().toPath();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+resource.getFilename())
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(newPath)) //préciser le type du contenu: image, pdf....
				.body(resource);
		
	}

}
