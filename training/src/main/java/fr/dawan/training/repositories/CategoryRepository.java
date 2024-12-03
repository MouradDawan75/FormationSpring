package fr.dawan.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.training.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Page<Category> findAllByNameContaining(String name, Pageable pageable);
	long countByNameContaining(String name);

}
