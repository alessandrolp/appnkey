package br.com.nkey.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nkey.dto.UsuarioLogin;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("login")
public class LoginRest {
	
	/**
	 * método implementado apenas para aparecer no swagger
	 * 
	 * @param usuario
	 */
	@ApiOperation("Realizar login")
	@PostMapping
	public void fakeLogin(@ApiParam(name = "Usuário", value = "Usuário") @RequestBody UsuarioLogin usuario) {
	    throw new IllegalStateException("Esse método não sera chamado, pois sera executado pelo spring security!");
	}

}
