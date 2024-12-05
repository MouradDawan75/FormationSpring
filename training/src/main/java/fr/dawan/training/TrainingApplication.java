package fr.dawan.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.training.interceptors.MyInterceptor;
import fr.dawan.training.interceptors.TokenInterceptor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@SecurityScheme(name = "BearerToken", type = SecuritySchemeType.HTTP, scheme = "Bearer", description = "Enter your token here:")
//@OpenAPIDefinition(info = @Info(title = "Backend API")) // doc extraite dans la classe SwaggerConfig
@SpringBootApplication
public class TrainingApplication {
	
	//Instancier la classe RestTemplate au démarrage de l'application
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
	//Crypt password
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private MyInterceptor myInterceptor;
	
	@Autowired
	private TokenInterceptor tokenInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}
	
	
	//Ajouter MyInterceptor à a conf. fournie par Spring
	//Récupérer la conf par défaut et ajouter l'intercepteur
	
	@Bean
	public WebMvcConfigurer getMvcConfig() {
		
		return new WebMvcConfigurer() {
			
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				//registry.addInterceptor(myInterceptor);
				registry.addInterceptor(tokenInterceptor);
			}
			
			//Liste des Front qui peuvent accéder aux ressources de cette API
			//Par défaut, seul les front qui se trouve dans le domaine: localhost qui peuvent avoir accès a cette API
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("*") // Fournir liste des front: ip:port qui auront accès à l'api
				.allowedMethods("GET","POST","DELETE","PUT","PATCH","PATCH", "HEAD","OPTIONS")
				.allowedHeaders("Content-Type","Authorization","accept","Origin","X-Requested-With","api_key");
			}
			
		};
	}
	
	/*
	 * CORS: Cross Origine Resources Sharing
	 */

}
