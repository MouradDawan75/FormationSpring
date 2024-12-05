package fr.dawan.training.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.LocationDto;
import fr.dawan.training.services.ILocationService;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
	
	@Autowired
	private ILocationService locationService;
	
	@GetMapping(produces = "application/json")
	public List<LocationDto> getAll() throws Exception{
		return locationService.importFromExterneSystem();
	}

}
