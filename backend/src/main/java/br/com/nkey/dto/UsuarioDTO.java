package br.com.nkey.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsuarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String nome;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String senha;
	@NotEmpty
	private String confirmacaoSenha;

}
