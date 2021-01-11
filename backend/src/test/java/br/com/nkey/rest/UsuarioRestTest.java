package br.com.nkey.rest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;

import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.UsuarioDTO;
import br.com.nkey.dto.UsuarioSalvoDTO;
import br.com.nkey.exception.handler.ApiErrorDetails;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MongoDbIntegrationTest
public class UsuarioRestTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String urlBase;
	
	@BeforeEach
	public void setup() {
		urlBase = "http://localhost:" + port + "/";
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveSalvarUsuarioComSucesso() {
		UsuarioDTO usuarioDTO = UsuarioDTO.builder()
			.nome("jose")
			.email("jose.silva@gmail.com")
			.senha("123Mudar")
			.confirmacaoSenha("123Mudar")
			.build();
		
		ResponseEntity<UsuarioSalvoDTO> responseEntity = restTemplate.postForEntity(urlBase + "usuario", usuarioDTO, UsuarioSalvoDTO.class);
		UsuarioSalvoDTO usuarioSalvo = responseEntity.getBody();
		assertAll(
				() -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
				() -> assertEquals(usuarioDTO.getNome(), usuarioSalvo.getNome()),
				() -> assertEquals(usuarioDTO.getEmail(), usuarioSalvo.getEmail())
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveFalharAoSalvarComEmailInvalido() {
		UsuarioDTO usuarioDTO = UsuarioDTO.builder()
				.nome("admin")
				.email("admin.com")
				.senha("123Mudar")
				.confirmacaoSenha("123Mudar")
				.build();
			
		ResponseEntity<Usuario> responseEntity = restTemplate.postForEntity(urlBase + "usuario", usuarioDTO, Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveRetornarBadRequestParaEmailRepetido() {
		UsuarioDTO usuarioDTO = UsuarioDTO.builder()
				.nome("admin")
				.email("admin@gmail.com")
				.senha("123Mudar")
				.confirmacaoSenha("123Mudar")
				.build();
			
		ResponseEntity<ApiErrorDetails> responseEntity = restTemplate.postForEntity(urlBase + "usuario", usuarioDTO, ApiErrorDetails.class);
		assertAll(
				() -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
				() -> assertEquals("Já existe um usuário com o email 'admin@gmail.com'", responseEntity.getBody().getMessage())
		);
		
	}
	
	@Test
	@MongoDataSet(value = "/dataset/usuario-admin.json", cleanBefore = true)
	public void deveRetornarBadRequestParaSenhaInvalida() {
		UsuarioDTO usuarioDTO = UsuarioDTO.builder()
				.nome("maria")
				.email("maria@gmail.com")
				.senha("123Mudar")
				.confirmacaoSenha("123456Mudar")
				.build();
			
			ResponseEntity<ApiErrorDetails> responseEntity = restTemplate.postForEntity(urlBase + "usuario", usuarioDTO, ApiErrorDetails.class);
			assertAll(
					() -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
					() -> assertEquals("Senhas não conferem, por favor confira e tente novamente", responseEntity.getBody().getMessage())
			);
	}
	
	

}
