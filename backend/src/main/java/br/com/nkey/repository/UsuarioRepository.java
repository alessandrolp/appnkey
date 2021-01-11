package br.com.nkey.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.nkey.domain.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	Optional<Usuario> findByEmail(String email);
	
}
