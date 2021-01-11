package br.com.nkey.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.nkey.domain.Cadastro;

@Repository
public interface CadastroRepository extends MongoRepository<Cadastro, String>{

}
