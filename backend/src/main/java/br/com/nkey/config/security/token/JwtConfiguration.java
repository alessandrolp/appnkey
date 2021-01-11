package br.com.nkey.config.security.token;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Configuration
@NoArgsConstructor
@Getter
public class JwtConfiguration {

	private String loginUrl = "/login/**";
	
	private String tokenPrefix = "Bearer ";
	
	private long expiration = 3600 * 1000; // 1 hora
	
	private String privateKey = "oqYcrn74aDvvjnt2fqPGzmf1ytdhcLRY";
	
}
