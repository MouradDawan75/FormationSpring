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
import fr.dawan.training.dto.SupplierDto;
import fr.dawan.training.services.ICategoryService;
import fr.dawan.training.services.IProductService;
import fr.dawan.training.services.ISupplierService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
	
	@Autowired
	private ISupplierService supplierService;
	
	
	
	//Liste avec pagination
	
	@GetMapping(value = {"/{page}/{size}/{search}", "/{page}/{size}"}, produces = "application/json")
	public List<SupplierDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable(value = "search", required = false) Optional<String> search) throws Exception{
		
		//par commance à 1 pour l'utilisateur et commance à 0 pour le Repository
		if(search.isPresent()) {
			return supplierService.getAllBy(page - 1, size, search.get());
		}else {
			return supplierService.getAllBy(page - 1, size, "");
		}
		
	}
	
	//Get By Id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getbyId(@PathVariable("id") int id) throws Exception{
		SupplierDto dto = supplierService.getById(id);
		if(dto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier with id = "+id+" not found.");
		}
	}
	
	//Compter le nombre de produit avec un filtre
	
	@GetMapping(value = {"/count/{search}", "/count"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value="search", required = false) Optional<String> search) throws Exception{
		
		if(search.isPresent()) {
			return supplierService.countBy(search.get());
		}else {
			return supplierService.countBy("");
		}
		
	}
	
	//Delete product
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) throws Exception{
		SupplierDto dto = supplierService.getById(id);
		
		if(dto != null) {
			supplierService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Supplier with id = "+id+" deleted.");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier with id = "+id+" not found.");
		}
	}
	
	//Save or update product
	
	@PostMapping(value="/save", produces = "application/json", consumes = "application/json")
	public ResponseEntity<SupplierDto> saveOrUpdate(@RequestBody SupplierDto dto) throws Exception{
		
		SupplierDto savedSup = supplierService.saveOrUpdate(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(savedSup);
				
		
	}


}
