package edu.eci.arsw.digitalqueue;

import edu.eci.arsw.digitalqueue.model.builder.UserBuilder;
import edu.eci.arsw.digitalqueue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalQueueApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializer(UserRepository userRepository) {
		return args -> userRepository.save(
				UserBuilder.anUser()
						.withId((long) 1)
						.withEmail("admin@mail.com")
						.withName("Administrator")
						.withPassword("admin")
						.build()
		);
	}

}
