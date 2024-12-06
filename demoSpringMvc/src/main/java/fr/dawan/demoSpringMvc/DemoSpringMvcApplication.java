package fr.dawan.demoSpringMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.demoSpringMvc.interceptors.LoginInterceptor;

@SpringBootApplication
public class DemoSpringMvcApplication {
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private LoginInterceptor loginInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringMvcApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer mvcConfigurer() {
		
		return new WebMvcConfigurer() {
			
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(loginInterceptor);
			}
			
			
			
		};
	}

}
