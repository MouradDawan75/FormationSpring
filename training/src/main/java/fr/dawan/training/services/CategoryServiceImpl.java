package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.CountDto;
import fr.dawan.training.entities.Category;
import fr.dawan.training.repositories.CategoryRepository;
import fr.dawan.training.tools.DtoTool;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getAllBy(int page, int size, String search) throws Exception {
		
		List<CategoryDto> result = new ArrayList<>();
		
		List<Category> categories = categoryRepository.findAllByNameContaining(search, PageRequest.of(page, size)).getContent();
		
		for(Category c :  categories) {
			result.add(DtoTool.convert(c, CategoryDto.class));
		}
		
		return result;
	}

	@Override
	public CountDto countBy(String search) throws Exception {
		CountDto dto = new CountDto();
		long nb = categoryRepository.countByNameContaining(search);
		dto.setNb(nb);
		return dto;
	}

	@Override
	public CategoryDto saveOrUpdate(CategoryDto catDto) throws Exception {
		Category category = DtoTool.convert(catDto, Category.class);
		Category savedCat = categoryRepository.saveAndFlush(category);
		
		return DtoTool.convert(savedCat, CategoryDto.class);
	}

	@Override
	public void deleteById(int id) throws Exception {
		categoryRepository.deleteById(id);
		
	}

	@Override
	public CategoryDto getById(int id) throws Exception {
		Optional<Category> optional = categoryRepository.findById(id);
		if(optional.isPresent()) {
			return DtoTool.convert( optional.get(), CategoryDto.class);
		}
		return null;
	}

}
