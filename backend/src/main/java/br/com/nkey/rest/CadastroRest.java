package br.com.nkey.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.nkey.domain.Cadastro;
import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.CadastroDTO;
import br.com.nkey.service.api.CadastroService;
import br.com.nkey.service.api.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(value = "Cadastro", tags = "Cadastro")
@RestController
@RequestMapping("cadastro")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastroRest extends CrudRestImpl<Cadastro> {
	
	private final CadastroService service; 
	
	@ApiOperation(value = "Salvar")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Void> save(@ApiParam(name = "Cadastro", value = "Cadastro que será salvo.") @RequestBody @Validated CadastroDTO cadastro,
			@AuthenticationPrincipal Usuario usuarioLogado) {
		Cadastro cadastroSalvo = service.save(cadastro, usuarioLogado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(cadastroSalvo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Alterar")
	@PutMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(
			@ApiParam(name = "id", value = "Id do cadastro que será alterado", required = true) final @PathVariable String id,
			@ApiParam(name = "Cadastro", value = "Cadastro que será salvo.") @RequestBody @Validated CadastroDTO cadastro) {
		service.update(id, cadastro);
	}

	@Override
	public CrudService<Cadastro> getService() {
		return this.service;
	}
	
}
