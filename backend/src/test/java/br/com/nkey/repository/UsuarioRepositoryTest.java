package br.com.nkey.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;

import br.com.nkey.domain.Usuario;

//@DataMongoTest
@MongoDbIntegrationTest
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Test
	public void deveSalvarUsuarioComSucesso() {
		Usuario usuario = Usuario.builder()
				.nome("jose")
				.email("jose.silva@gmail.com")
				.senha("123mudar")
				.build();
		
		repository.save(usuario);
	}

}
