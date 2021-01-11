package br.com.nkey.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;

import br.com.nkey.domain.Cadastro;
import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.CadastroDTO;
import br.com.nkey.exception.DocumentNotFoundException;
import br.com.nkey.service.api.CadastroService;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@MongoDbIntegrationTest
public class CadastroServiceTest {
	
	@Autowired
	private CadastroService service;
	
	@Test
	public void deveSalvarComSucesso() {
		Cadastro cadastro = service.save(new CadastroDTO("maria"), getUsuarioLogado());
		assertAll(
				() -> assertNotNull(cadastro.getId()),
				() -> assertEquals("maria", cadastro.getNome()),
				() -> assertNotNull(cadastro.getData()),
				() -> assertNotNull(cadastro.getUsuario().getId()),
				() -> assertNotNull(cadastro.getUsuario().getEmail()) 
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveRecuperarPorIdComSucesso() {
		Cadastro cadastro = service.findById("55f3ed00b1375a48e618300a");
		assertAll(
				() -> assertNotNull(cadastro.getId()),
				() -> assertEquals("maria", cadastro.getNome()),
				() -> assertNotNull(cadastro.getData()),
				() -> assertEquals("55f3ed00b1375a48e618300b", cadastro.getUsuario().getId()),
				() -> assertEquals("admin@gmail.com", cadastro.getUsuario().getEmail()) 
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveBuscarTodosComSucesso() {
		List<Cadastro> cadastros = service.findAll();
		assertEquals(1, cadastros.size());
		
		service.save(new CadastroDTO("jose"), getUsuarioLogado());
		cadastros = service.findAll();
		assertEquals(2, cadastros.size());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveRemoverPorIdComSucesso() {
		service.deleteById("55f3ed00b1375a48e618300a");
		List<Cadastro> cadastros = service.findAll();
		assertEquals(0, cadastros.size());
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveAlterarComSucesso() {
		CadastroDTO cadastroDTO = new CadastroDTO("pedro");
		service.update("55f3ed00b1375a48e618300a", cadastroDTO);
		Cadastro cadastro = service.findById("55f3ed00b1375a48e618300a");
		assertAll(
				() -> assertNotNull(cadastro.getId()),
				() -> assertEquals("pedro", cadastro.getNome()),
				() -> assertNotNull(cadastro.getData()),
				() -> assertEquals("55f3ed00b1375a48e618300b", cadastro.getUsuario().getId()),
				() -> assertEquals("admin@gmail.com", cadastro.getUsuario().getEmail()) 
		);
	}
	
	@Test
	@MongoDataSet(value = "/dataset/cadastro-teste.json", cleanBefore = true)
	public void deveFalharAoAlterarCadastroPorIdInexistente() {
		CadastroDTO cadastroDTO = new CadastroDTO("pedro");
		assertThrows(
				DocumentNotFoundException.class,
				() -> service.update("55f3ed00b1375a48e618300b", cadastroDTO),
				"Registro não encontrado pelo id informado"
		);
	}
	
	private Usuario getUsuarioLogado() {
		Usuario usuario = Usuario.builder()
				.id(UUID.randomUUID().toString())
				.email("admin@gmail.com")
				.permissao("ROLE_ADMIN")
				.build();
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	
}
