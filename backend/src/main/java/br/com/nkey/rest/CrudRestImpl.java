package br.com.nkey.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.nkey.domain.BaseDocument;
import br.com.nkey.service.api.CrudService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public abstract class CrudRestImpl<T extends BaseDocument> implements CrudRest<T>{
	
	public abstract CrudService<T> getService();

	@ApiOperation(value = "Buscar por id")
	@GetMapping("{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@Override
	public T findById(@ApiParam(name = "id", value = "id",required = true) @PathVariable final String id) {
		return getService().findById(id);
	}

	@ApiOperation(value = "Buscar todos")
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	@Override
	public List<T> findAll() {
		return getService().findAll();
	}

	@ApiOperation(value = "Remover por id")
	@DeleteMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Override
	public void deleteById(@ApiParam(name = "id", value = "id", required = true) final @PathVariable String id) {
		getService().deleteById(id);
	}

}
