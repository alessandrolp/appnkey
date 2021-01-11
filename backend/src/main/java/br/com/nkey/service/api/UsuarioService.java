package br.com.nkey.service.api;

import br.com.nkey.dto.UsuarioDTO;
import br.com.nkey.dto.UsuarioSalvoDTO;

public interface UsuarioService {
	
	UsuarioSalvoDTO save(UsuarioDTO usuarioDTO);

}
