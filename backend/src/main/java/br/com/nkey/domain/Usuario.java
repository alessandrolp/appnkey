package br.com.nkey.domain;

import javax.validation.constraints.Email;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "usuario")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "email", callSuper = false)
public class Usuario extends BaseDocument {
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	private String permissao = "ADMIN";
	
	public Usuario(Usuario applicationUser) {
		this(applicationUser.getId(), applicationUser.getNome(), applicationUser.getEmail(), applicationUser.getSenha(), applicationUser.getPermissao());
	}

	@Builder
	private Usuario(String id, String nome, String email, String senha, String permissao) {
		super(id);
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.permissao = permissao != null ? permissao : "ADMIN";
	}
	
	
	
}
