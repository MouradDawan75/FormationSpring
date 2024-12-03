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

import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.ProductDto;
import fr.dawan.training.services.IProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	
	@Value("${storage.folder}")
	private String storageFolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//Liste avec pagination
	
	@GetMapping(value = {"/{page}/{size}/{search}", "/{page}/{size}"}, produces = "application/json")
	public List<ProductDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable(value = "search", required = false) Optional<String> search) throws Exception{
		
		//par commance à 1 pour l'utilisateur et commance à 0 pour le Repository
		if(search.isPresent()) {
			return productService.getAllBy(page - 1, size, search.get());
		}else {
			return productService.getAllBy(page - 1, size, "");
		}
		
	}
	
	//Get By Id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getbyId(@PathVariable("id") int id) throws Exception{
		ProductDto dto = productService.getById(id);
		if(dto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = "+id+" not found.");
		}
	}
	
	//Compter le nombre de produit avec un filtre
	
	@GetMapping(value = {"/count/{search}", "/count"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value="search", required = false) Optional<String> search) throws Exception{
		
		if(search.isPresent()) {
			return productService.countBy(search.get());
		}else {
			return productService.countBy("");
		}
		
	}
	
	//Delete product
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) throws Exception{
		ProductDto dto = productService.getById(id);
		if(dto != null) {
			productService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Product with id = "+id+" deleted.");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = "+id+" not found.");
		}
	}
	
	//Save or update product
	
	@PostMapping(value="/save", produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<ProductDto> saveOrUpdate(@RequestParam("productDtoStr") String productDtoStr, 
			@RequestParam("file") MultipartFile file) throws Exception{
		
		//Gestion du productDtoStr -> convertir en Objet
		ProductDto dto = objectMapper.readValue(productDtoStr, ProductDto.class);
		
		//Construction du path + personnaliser le nom du fichier
		String filePath = "/"+dto.getDescription()+"-"+file.getOriginalFilename();
		File f = new File(storageFolder+filePath);
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
		bos.write(file.getBytes());
		
		dto.setImagePath(filePath);
		
		ProductDto savedDto = productService.saveOrUpdate(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
				
		
	}
	
	//Get product image by id
	
	@GetMapping(value="/image/{productId}", produces = "application/octet-stream")
	public ResponseEntity<Resource> getProductImage(@PathVariable("productId") int id) throws Exception{
		
		//Appel de getById pour récupérer le produit depuis la BD
		ProductDto dto = productService.getById(id);
		
		Path path = Paths.get(storageFolder).resolve(dto.getImagePath());
		Resource resource = new UrlResource(path.toUri());
		
		Path newPath = resource.getFile().toPath();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+resource.getFilename())
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(newPath)) //préciser le type du contenu: image, pdf....
				.body(resource);
		
	}

}
