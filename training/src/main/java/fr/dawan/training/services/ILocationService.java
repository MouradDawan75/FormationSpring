package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.LocationDto;

public interface ILocationService {
	
	List<LocationDto> importFromExterneSystem() throws Exception;

}
