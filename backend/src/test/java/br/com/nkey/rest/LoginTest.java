package br.com.nkey.rest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;

import br.com.nkey.dto.UsuarioLogin;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MongoDbIntegrationTest
public class LoginTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	@BeforeEach
	public void setup() {
		url = "http://localhost:" + port + "/login";
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveRealizarLoginComSucesso() {
		UsuarioLogin usuarioLogin = new UsuarioLogin("admin@gmail.com", "123mudar");
		
		ResponseEntity<Void> responseLogin = restTemplate.postForEntity(url, usuarioLogin, Void.class);
		final String token = responseLogin.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
		assertAll(
				() -> assertNotNull(token),
				() -> assertEquals(HttpStatus.OK, responseLogin.getStatusCode())
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveFalharAoFazerLoginComSenhaIncorreta() {
		UsuarioLogin usuarioLogin = new UsuarioLogin("admin@gmail.com", "1234mudar");
		
		ResponseEntity<Void> responseLogin = restTemplate.postForEntity(url, usuarioLogin, Void.class);
		
		assertAll(
				() -> assertNull(responseLogin.getHeaders().get(HttpHeaders.AUTHORIZATION)),
				() -> assertEquals(HttpStatus.UNAUTHORIZED, responseLogin.getStatusCode())
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveFalharAoFazerLoginComEmailIncorreto() {
		UsuarioLogin usuarioLogin = new UsuarioLogin("emailincorreto@gmail.com", "1234mudar");
		
		ResponseEntity<Void> responseLogin = restTemplate.postForEntity(url, usuarioLogin, Void.class);
		
		assertAll(
				() -> assertNull(responseLogin.getHeaders().get(HttpHeaders.AUTHORIZATION)),
				() -> assertEquals(HttpStatus.UNAUTHORIZED, responseLogin.getStatusCode())
		);
	}

}
