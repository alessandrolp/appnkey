package br.com.nkey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import br.com.nkey.domain.Cadastro;
import br.com.nkey.domain.Usuario;
import br.com.nkey.domain.UsuarioCadastro;
import br.com.nkey.dto.CadastroDTO;
import br.com.nkey.repository.CadastroRepository;
import br.com.nkey.service.api.CadastroService;

@Service
public class CadastroServiceImpl extends CrudServiceImpl<Cadastro> implements CadastroService {
	
	@Autowired
	public CadastroServiceImpl(CadastroRepository repository) {
		super(repository);
	}
	
	@Override
	public Cadastro save(CadastroDTO cadastroDTO, @AuthenticationPrincipal Usuario usuarioLogado) {
		return repository.save(Cadastro.of(cadastroDTO.getNome(), UsuarioCadastro.of(usuarioLogado)));
	}
	
	@Override
	public void update(String id, CadastroDTO cadastroDTO) {
		Cadastro cadastro = findById(id);
		cadastro.setNome(cadastroDTO.getNome());
		repository.save(cadastro);
	}

}
