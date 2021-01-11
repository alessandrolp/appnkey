package br.com.nkey.rest;

import java.util.List;

import br.com.nkey.domain.BaseDocument;

public interface CrudRest<T extends BaseDocument> {
	
	T findById(String id);
	
	List<T> findAll();
	
	void deleteById(String id);

}
