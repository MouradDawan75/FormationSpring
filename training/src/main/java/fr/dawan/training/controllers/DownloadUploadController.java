package fr.dawan.training.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.dawan.training.entities.Product;

@RestController
@RequestMapping("/api/file")
public class DownloadUploadController {
	
	@Value("${storage.folder}")
	private String storageFolder;
	
	@PostMapping(value="/upload-image/{productId}", produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<Product> uploadImage(@PathVariable("productId") int id, 
			@RequestParam("file") MultipartFile file) throws Exception{
		
		//Construction du path + personnaliser le nom du fichier
		String filePath = "/"+id+"-"+file.getOriginalFilename();
		File f = new File(storageFolder+filePath);
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
		bos.write(file.getBytes());
		
		//Convertir le productStr fournit dans form-data en Product
		Product p = new Product();
		p.setId(id);
		p.setImagePath(filePath); //la BD contient le chemin de l'image
		
		//sauvegarder le produit en BD
		
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
				
		
	}
	
	@GetMapping(value="/image/{productId}", produces = "application/octet-stream")
	public ResponseEntity<Resource> getProductImage(@PathVariable("productId") int id) throws Exception{
		
		//Appel de getById pour récupérer le produit depuis la BD
		Product p = new Product();
		p.setId(id);
		p.setImagePath("1-dell.jpg");
		
		Resource resource = null;
		try {
			
			Path path = Paths.get(storageFolder).resolve(p.getImagePath());
			resource = new UrlResource(path.toUri());
			
		}catch (Exception e) {
			throw e;
		}
		
		Path newPath = resource.getFile().toPath();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+resource.getFilename())
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(newPath)) //préciser le type du contenu: image, pdf....
				.body(resource);
		
	}

}
