package agh.wd.flatrenting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"agh.wd.flatrenting.entities"})
public class FlatrentingApplication {

	public static void main(String[] args) {

		SpringApplication.run(FlatrentingApplication.class, args);

	}

//	@Bean
//	ApplicationRunner init(UserRepository repository) {
//		return args ->
//				repository.save(new User("admin", "admin@google.com", "admin", "997"));
//	}

}
