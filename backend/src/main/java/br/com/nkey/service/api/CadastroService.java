package br.com.nkey.service.api;

import br.com.nkey.domain.Cadastro;
import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.CadastroDTO;

public interface CadastroService extends CrudService<Cadastro> {
	
	Cadastro save(CadastroDTO cadastro, Usuario usuarioLogado);
	
	void update(String id, CadastroDTO cadastro);

}
