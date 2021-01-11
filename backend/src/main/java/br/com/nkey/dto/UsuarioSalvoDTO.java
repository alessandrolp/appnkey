package br.com.nkey.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class UsuarioSalvoDTO {
	
	@NonNull
	private String nome;
	@NonNull
	private String email;

}
