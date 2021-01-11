package br.com.nkey.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.UsuarioDTO;
import br.com.nkey.exception.BusinessRuntimeException;
import br.com.nkey.repository.UsuarioRepository;
import br.com.nkey.service.impl.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository repository;
	
	@InjectMocks
	private UsuarioServiceImpl usuarioService;
	
	@Test
	public void deveFalharAoCadastroEmailRepetido() {
		Usuario usuarioRetornadoPorEmail = Usuario.builder().nome("jose")
			.email("jose.silva@gmail.com")
			.senha("123mudar")
			.build();
		
		when(repository.findByEmail("jose.silva@gmail.com"))
			.thenReturn(Optional.of(usuarioRetornadoPorEmail));
		
		UsuarioDTO usuarioSalvar = UsuarioDTO.builder()
				.nome("jose")
				.email("jose.silva@gmail.com")
				.senha("123mudar")
				.confirmacaoSenha("123mudar")
				.build();
		
		assertThrows(
				BusinessRuntimeException.class,
				() -> usuarioService.save(usuarioSalvar),
				"Já existe um usuário com o email 'jose.silva@gmail.com'"
		);
	}
	
	@Test
	public void deveFalharAoSalvarUsuarioComSenhasDiferentes() {
		when(repository.findByEmail("jose.silva@gmail.com"))
			.thenReturn(Optional.empty());
		
		UsuarioDTO usuarioSalvar = UsuarioDTO.builder()
				.nome("jose")
				.email("jose.silva@gmail.com")
				.senha("123mudar")
				.confirmacaoSenha("123456789mudar")
				.build();
		
		assertThrows(
				BusinessRuntimeException.class,
				() -> usuarioService.save(usuarioSalvar),
				"Senhas não conferem, por favor confira e tente novamente"
		);
	}

}
