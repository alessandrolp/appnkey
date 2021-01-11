package br.com.nkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class NkeyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NkeyBackendApplication.class, args);
	}

}
