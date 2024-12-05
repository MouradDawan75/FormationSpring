package fr.dawan.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.dawan.training.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("From User u where u.email = :email")
	User findByEmail(@Param("email") String email);

}
