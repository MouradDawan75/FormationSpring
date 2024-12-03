package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.CountDto;
import fr.dawan.training.dto.ProductDto;
import fr.dawan.training.entities.Category;
import fr.dawan.training.entities.Product;
import fr.dawan.training.repositories.CategoryRepository;
import fr.dawan.training.repositories.ProductRepository;
import fr.dawan.training.tools.DtoTool;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<ProductDto> getAllBy(int page, int size, String search) throws Exception {
		
		List<ProductDto> resultat = new ArrayList();
		
		List<Product> prods = productRepository.findAllByDescriptionContaining(search, PageRequest.of(page, size)).getContent();
		
		//ModelMapper: permet de convertir une Entity en Dto et vice versa
		/*
		 * Pour que le mapping automatique fonctionne: Entity et DTO dovient avoir le mm nom et le mm type d'attribut
		 */
		
		for(Product p: prods) {
			resultat.add(DtoTool.convert(p, ProductDto.class));
		}
		
		
		return resultat;
	}

	@Override
	public CountDto countBy(String search) throws Exception {
		long nb =productRepository.countByDescriptionContaining(search);
		CountDto dto = new CountDto();
		dto.setNb(nb);
		return dto;
	}

	@Override
	public ProductDto saveOrUpdate(ProductDto productDto) throws Exception {
		
		Product p = DtoTool.convert(productDto, Product.class);
		
		//Gestion du ManyToOne avec Category (remplir la colonne category_id)
		Category cat = categoryRepository.findById(productDto.getCategoryId()).get();
		p.setCategory(cat);
		
		Product savedProduct =productRepository.saveAndFlush(p); //si id = 0 -> commande insert -> si id != 0 commande update
		
		
		return DtoTool.convert(savedProduct, ProductDto.class);
	}

	@Override
	public void deleteById(int id) throws Exception {
		productRepository.deleteById(id);
		
	}

	@Override
	public ProductDto getById(int id) throws Exception {
		Optional<Product> optional = productRepository.findById(id);
		if(optional.isPresent()) {
			return  DtoTool.convert(optional.get(), ProductDto.class);
		}
		return null;
	}

}
