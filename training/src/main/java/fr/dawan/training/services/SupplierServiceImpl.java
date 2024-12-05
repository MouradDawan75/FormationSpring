package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.SupplierDto;
import fr.dawan.training.entities.Product;
import fr.dawan.training.entities.Supplier;
import fr.dawan.training.repositories.ProductRepository;
import fr.dawan.training.repositories.SupplierRepository;
import fr.dawan.training.tools.DtoTool;

@Service
public class SupplierServiceImpl implements ISupplierService{
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<SupplierDto> getAllBy(int page, int size, String search) throws Exception {
		
		List<SupplierDto> result = new ArrayList<>();
		List<Supplier> suppliers = supplierRepository.findAllByNameContaining(search, PageRequest.of(page, size)).getContent();
		for(Supplier s:suppliers) {
			result.add(DtoTool.convert(s, SupplierDto.class));
		}
		
		return result;
	}

	@Override
	public CountDto countBy(String search) throws Exception {
		CountDto dto = new CountDto();
		long nb = supplierRepository.countByNameContaining(search);
		dto.setNb(nb);
		return dto;
	}

	@Override
	public SupplierDto saveOrUpdate(SupplierDto suppDto) throws Exception {
		
		Supplier supplier = DtoTool.convert(suppDto, Supplier.class);
		
		//Gestion du ManyToMany: Supplier contient une liste de Products - Le SupplierDto à fournit une d'id
		for(int id: suppDto.getProductsIds()) {
			Product product = productRepository.findById(id).get();
			supplier.getProducts().add(product);
			product.getSuppliers().add(supplier);
		}
		
		supplier.getProducts().remove(null); // suppression des product null
		Supplier savedSupplier = supplierRepository.saveAndFlush(supplier);
		
		return DtoTool.convert(savedSupplier, SupplierDto.class);
	}

	@Override
	public void deleteById(int id) throws Exception {
		supplierRepository.deleteById(id);
		
	}

	@Override
	public SupplierDto getById(int id) throws Exception {
		Optional<Supplier> optional = supplierRepository.findById(id);
		if(optional.isPresent()) {
			return DtoTool.convert(optional.get(), SupplierDto.class);
		}
		return null;
	}

}
