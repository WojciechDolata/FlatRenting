package agh.wd.flatrenting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"agh.wd.flatrenting.entities"})
public class FlatrentingApplication {

	public static void main(String[] args) {

		SpringApplication.run(FlatrentingApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200");
			}
		};
	}

//	@Bean
//	ApplicationRunner init(UserRepository repository) {
//		return args ->
//				repository.save(new User("admin", "admin@google.com", "admin", "997"));
//	}

}
