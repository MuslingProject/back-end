package swu.musling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing 활성화
@EnableScheduling
public class MuslingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuslingApplication.class, args);
	}

}
