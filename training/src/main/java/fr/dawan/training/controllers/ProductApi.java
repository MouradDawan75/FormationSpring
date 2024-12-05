package fr.dawan.training.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Cette interface permet de documentée l'api via Swagger
 */

@SecurityRequirement(name = "BearerToken")
@Tag(name = "Product", description = "The product Api")
public interface ProductApi {

	@Operation(summary = "Get all product pages", description = "Get products from Database")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Return list of product"))
	List<ProductDto> getAllBy(
			@Parameter(description = "page: page number", required = true) int page, 
			@Parameter(description = "size: number of product in page", required = true) int size, 
			@Parameter(description = "searchOption: filter the product by description", required = false) Optional<String> search) throws Exception;

	@Operation(summary = "Get by id", description = "Get product by id from Database")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Return product in body if exist."),
			@ApiResponse(responseCode = "401", description = "The product with given id was not found.")
			})
	ResponseEntity<Object> getbyId(int id) throws Exception;

	CountDto countBy(Optional<String> search) throws Exception;

	ResponseEntity<String> deleteById(int id) throws Exception;

	ResponseEntity<ProductDto> saveOrUpdate(String productDtoStr, MultipartFile file) throws Exception;

	ResponseEntity<Resource> getProductImage(int id) throws Exception;

}