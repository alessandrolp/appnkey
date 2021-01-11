package br.com.nkey.config.security.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenConverter {

	@Autowired
	private JwtConfiguration jwtConfiguration;
	
	@SneakyThrows
	public String decryptToken(String encryptedToken) {
		log.info("Descriptografando token...");
		JWEObject jweObject = JWEObject.parse(encryptedToken);
		DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());
		jweObject.decrypt(directDecrypter);
		log.info("Token descriptografado, returnando token assinado...");
		return jweObject.getPayload().toSignedJWT().serialize();
	}
	
	@SneakyThrows
	public void validateToken(String signedToken) {
		log.info("Iniciando validacao da assinatura do token...");
		SignedJWT signedJWT = SignedJWT.parse(signedToken);
		log.info("Recuperando chave publica");
		RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
		log.info("Chave publica recuperada, validando assinatura...");
		if(!signedJWT.verify(new RSASSAVerifier(publicKey))) {
			throw new AccessDeniedException("Assinatura invalida!");
		}
		log.info("Assinatura valida, verificando tempo de expiracao do token");
		if(signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date())) {
			throw new AccessDeniedException("Token expirado!");
		}
		log.info("validacao completada");
	}
	
}
