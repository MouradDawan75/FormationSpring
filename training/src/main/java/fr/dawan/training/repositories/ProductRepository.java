package fr.dawan.training.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.Product;

@Repository // non nécessaire car implémentée dans JpaRepository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	//finder méthodes de Spring Data
	//lien doc: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
	// From Product p wher p.description like%:description% *** JP-QL
	Page<Product> findAllByDescriptionContaining(String description, Pageable pageable);

	long countByDescriptionContaining(String description);
	
	//ManyToOne avec Category
	
	//Commande JP-QL: interroge les classes objets
	
	@Query(value = "From Product p where p.category.id = :id")
	List<Product> findAllByCategoryId(@Param("id") int id);
	
	//Requête Sql native: pointe vers les tables en BD
	
	@Query(nativeQuery = true, value = "select * from Product p inner join Category c on (p.category_id = c.ic) where c.id = :id")
	List<Product> findSqlNativeCategoryId(@Param("id") int id);
	
}
