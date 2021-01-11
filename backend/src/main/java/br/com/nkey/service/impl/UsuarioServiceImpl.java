package br.com.nkey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.UsuarioDTO;
import br.com.nkey.dto.UsuarioSalvoDTO;
import br.com.nkey.exception.BusinessRuntimeException;
import br.com.nkey.repository.UsuarioRepository;
import br.com.nkey.service.api.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UsuarioSalvoDTO save(UsuarioDTO usuarioDTO) {
		repository.findByEmail(usuarioDTO.getEmail()).ifPresent(u -> {
			throw new BusinessRuntimeException(String.format("Já existe um usuário com o email '%s'", u.getEmail()));
		});
		validarSenha(usuarioDTO);
		Usuario usuario = Usuario.builder()
			.nome(usuarioDTO.getNome())
			.email(usuarioDTO.getEmail())
			.senha(passwordEncoder.encode(usuarioDTO.getSenha()))
			.build();
		repository.save(usuario);
		
		return new UsuarioSalvoDTO(usuario.getNome(), usuario.getEmail());
	}
	
	private static void validarSenha(UsuarioDTO usuarioDTO) {
		if(!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
			throw new BusinessRuntimeException("Senhas não conferem, por favor confira e tente novamente");
		}
	}

}
