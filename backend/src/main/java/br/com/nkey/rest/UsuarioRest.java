package br.com.nkey.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nkey.dto.UsuarioDTO;
import br.com.nkey.dto.UsuarioSalvoDTO;
import br.com.nkey.service.api.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioRest {
	
	@Autowired
	private final UsuarioService service;
	
	@ApiOperation(value = "Salvar usuário")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioSalvoDTO save(@ApiParam(name = "Usuário", value = "Usuário que será salvo.") @RequestBody @Validated UsuarioDTO usuarioDTO) {
		return service.save(usuarioDTO);
	}

}
