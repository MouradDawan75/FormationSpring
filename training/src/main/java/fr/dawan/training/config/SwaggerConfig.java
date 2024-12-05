package fr.dawan.training.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi() {
		
		Server devSever = new Server();
		devSever.setUrl("http://localhost:8085");
		devSever.setDescription("Server URL in Development environment");
		
		Server prodServer = new Server();
		prodServer.setUrl("http://localhost:8080");
		prodServer.setDescription("Server URL in Production environment");
		
		Contact contact = new Contact();
		contact.setEmail("contact@dawan.fr");
		contact.setName("Dawan");
		contact.setUrl("https://www.dawan.fr");
		
		License licence = new License().name("Dawan licence").url("https://dawan.fr/licence/mit");
		
		Info info = new Info()
				.title("Spring Boot Rest API")
				.version("1.0")
				.contact(contact)
				.description("This Api exposes endpoints to manage tutorials....")
				.termsOfService("https://dawan.fr/terms").license(licence);
		
		return new OpenAPI().info(info).servers(List.of(devSever, prodServer));
		
	}
	 
	

}
