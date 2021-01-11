package br.com.nkey.service.api;

import java.util.List;

import br.com.nkey.domain.BaseDocument;

public interface CrudService<T extends BaseDocument> {
	
	T findById(String id);
	
	List<T> findAll();
	
	void deleteById(String id);
	
}
