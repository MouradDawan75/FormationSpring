package fr.dawan.training.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.ProductDto;
import fr.dawan.training.services.ICategoryService;
import fr.dawan.training.services.IProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
	
	
	
	//Liste avec pagination
	
	@GetMapping(value = {"/{page}/{size}/{search}", "/{page}/{size}"}, produces = "application/json")
	public List<CategoryDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable(value = "search", required = false) Optional<String> search) throws Exception{
		
		//par commance à 1 pour l'utilisateur et commance à 0 pour le Repository
		if(search.isPresent()) {
			return categoryService.getAllBy(page - 1, size, search.get());
		}else {
			return categoryService.getAllBy(page - 1, size, "");
		}
		
	}
	
	//Get By Id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getbyId(@PathVariable("id") int id) throws Exception{
		CategoryDto dto = categoryService.getById(id);
		if(dto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id = "+id+" not found.");
		}
	}
	
	//Compter le nombre de produit avec un filtre
	
	@GetMapping(value = {"/count/{search}", "/count"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value="search", required = false) Optional<String> search) throws Exception{
		
		if(search.isPresent()) {
			return categoryService.countBy(search.get());
		}else {
			return categoryService.countBy("");
		}
		
	}
	
	//Delete product
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) throws Exception{
		CategoryDto dto = categoryService.getById(id);
		
		if(dto != null) {
			categoryService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Category with id = "+id+" deleted.");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id = "+id+" not found.");
		}
	}
	
	//Save or update product
	
	@PostMapping(value="/save", produces = "application/json", consumes = "application/json")
	public ResponseEntity<CategoryDto> saveOrUpdate(@RequestBody CategoryDto cat) throws Exception{
		
		CategoryDto savedCat = categoryService.saveOrUpdate(cat);
		
		return ResponseEntity.status(HttpStatus.OK).body(savedCat);
				
		
	}


}
