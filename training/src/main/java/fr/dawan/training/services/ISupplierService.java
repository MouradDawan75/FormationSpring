package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.SupplierDto;

public interface ISupplierService {
	
	List<SupplierDto> getAllBy(int page, int size, String search) throws Exception;
	CountDto countBy(String search) throws Exception;
	SupplierDto saveOrUpdate(SupplierDto suppDto) throws Exception;
	void deleteById(int id) throws Exception;
	SupplierDto getById(int id) throws Exception;

}
