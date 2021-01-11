package br.com.nkey.config.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.nkey.domain.Usuario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityContextUtil {

	private SecurityContextUtil() {}
	
	public static void setSecurityContext(SignedJWT signedJWT) {
		try {
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
			String email = claims.getSubject();
			if(email == null) {
				throw new JOSEException("email nao encontrado no token JWT");
			}
			
			List<String> authorities = claims.getStringListClaim("authorities");
			Usuario usuario = Usuario.builder()
					.id(claims.getStringClaim("userId"))
					.email(email)
					.permissao(String.join(",", authorities))
					.build();
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null, createAuthorities(authorities));
			auth.setDetails(signedJWT.serialize());
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {
			log.error("Erro ao configurar SecurityContext", e);
			SecurityContextHolder.clearContext();
		}
	}
	
	private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities){
		return authorities.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	
}
