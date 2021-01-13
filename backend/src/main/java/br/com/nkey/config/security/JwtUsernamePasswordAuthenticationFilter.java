package br.com.nkey.config.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nimbusds.jwt.SignedJWT;

import br.com.nkey.config.security.token.JwtConfiguration;
import br.com.nkey.config.security.token.TokenFactory;
import br.com.nkey.domain.Usuario;
import br.com.nkey.dto.UsuarioLogin;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtConfiguration jwtConfiguration;
	private final TokenFactory tokenFactory;
	
	@Override
	@SneakyThrows
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		log.info("Iniciando antenticacao...");
		UsuarioLogin usuarioLogin = new ObjectMapper().readValue(request.getInputStream(), UsuarioLogin.class);
		if(usuarioLogin == null) {
			throw new UsernameNotFoundException("Erro ao recuperar email e/ou senha do usuario."); 
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuarioLogin.getEmail(), usuarioLogin.getSenha(), Collections.emptyList());
		Usuario usuario = Usuario.builder()
				.email(usuarioLogin.getEmail())
				.senha(usuarioLogin.getSenha())
				.build();
		usernamePasswordAuthenticationToken.setDetails(usuario);
		return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}
	
	@Override
	@SneakyThrows
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		log.info("Autenticacao com sucesso para o usuario '{}'. Iniciando geração do token...", authResult.getName());

		Usuario usuario = (Usuario) authResult.getPrincipal();

		// SignedJWT signedJWT = tokenFactory.createSignedJWT(authResult);
		// String encryptToken = tokenFactory.encryptToken(signedJWT);

		String encryptToken = tokenFactory.generateToken(usuario);
		
		log.info("Token gerado com sucesso! Adicionando ao response header.");
		
		response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + HttpHeaders.AUTHORIZATION);
		response.addHeader(HttpHeaders.AUTHORIZATION, jwtConfiguration.getTokenPrefix() + encryptToken);
	}
	
}

