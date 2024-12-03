package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.ProductDto;

public interface IProductService {
	
	List<ProductDto> getAllBy(int page, int size, String search) throws Exception;
	CountDto countBy(String search) throws Exception;
	ProductDto saveOrUpdate(ProductDto productDto) throws Exception;
	void deleteById(int id) throws Exception;
	ProductDto getById(int id) throws Exception;

}
