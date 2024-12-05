package fr.dawan.training.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.dawan.training.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	
	Page<Supplier> findAllByNameContaining(String name, Pageable pageable);
	long countByNameContaining(String name);
	
	//ManyToMany avec product
	@Query(value="From Supplier s LEFT JOIN FETCH s.products p where p.id = :id")
	List<Supplier> findAllByProductId(@Param("id") int id);
	
	//Demande explicite du chargement de la liste des products associés- car par défaut le chargement est Lazy
	@Query(value="From Supplier s LEFT JOIN FETCH s.products p where s.id = : id")
	Optional<Supplier> findById(@Param("id") int id);

}
