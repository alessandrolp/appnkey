package br.com.nkey.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import br.com.nkey.config.security.token.JwtConfiguration;
import br.com.nkey.config.security.token.TokenConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
	
	private final JwtConfiguration jwtConfiguration;
	private final TokenConverter tokenConverter;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(header == null || !header.startsWith(jwtConfiguration.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.replace(jwtConfiguration.getTokenPrefix(), "").trim();
		
		SecurityContextUtil.setSecurityContext(decryptValidation(token));
		filterChain.doFilter(request, response);
	}
	
	@SneakyThrows
	private SignedJWT decryptValidation(String encryptedToken) {
		String signedToken = tokenConverter.decryptToken(encryptedToken);
		tokenConverter.validateToken(signedToken);
		return SignedJWT.parse(signedToken);
	}
	
}
