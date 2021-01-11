package br.com.nkey.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UsuarioCadastro {
	
	private String id;
	
	private String email;
	
	public static UsuarioCadastro of (Usuario usuarioLogado) {
		return new UsuarioCadastro(usuarioLogado.getId(), usuarioLogado.getEmail());
	}

}
