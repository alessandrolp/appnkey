package br.com.nkey.service.impl;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.nkey.domain.BaseDocument;
import br.com.nkey.exception.DocumentNotFoundException;
import br.com.nkey.service.api.CrudService;

public abstract class CrudServiceImpl<T extends BaseDocument> implements CrudService<T> {
	
	protected final MongoRepository<T, String> repository;
	
	public CrudServiceImpl(MongoRepository<T, String> repository) {
		this.repository = repository;
	}

	@Override
	public T findById(String id) {
		return repository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Registro não encontrado pelo id informado"));
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

}
