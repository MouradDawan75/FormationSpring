package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.CountDto;

public interface ICategoryService {
	
	List<CategoryDto> getAllBy(int page, int size, String search) throws Exception;
	CountDto countBy(String search) throws Exception;
	CategoryDto saveOrUpdate(CategoryDto catDto) throws Exception;
	void deleteById(int id) throws Exception;
	CategoryDto getById(int id) throws Exception;

}
