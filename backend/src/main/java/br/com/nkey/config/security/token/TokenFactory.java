package br.com.nkey.config.security.token;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.nkey.domain.Usuario;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenFactory {
	
	@Autowired
	private JwtConfiguration jwtConfiguration;
	
	@SneakyThrows
	public SignedJWT createSignedJWT(Authentication authentication) {
		log.info("Iniciando a assinatura do token");
		Usuario usuario = (Usuario) authentication.getPrincipal();
		JWTClaimsSet jwtClaimsSet = createJWTClaimsSet(authentication, usuario);
		KeyPair rsaKeys = generateKeyPair();
		
		JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic())
				.keyID(UUID.randomUUID().toString())
				.build();
		
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
				.jwk(jwk)
				.type(JOSEObjectType.JWT)
				.build();
		
		SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
		
		RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());
		signedJWT.sign(signer);
		
		log.info("Token assinado com sucesso");
		return signedJWT;
	}
	
	private JWTClaimsSet createJWTClaimsSet(Authentication authentication, Usuario usuario) {
		log.info("Adicionando informacoes para o token do usuario '{}'", usuario);
		return new JWTClaimsSet.Builder()
				.subject(usuario.getEmail())
				.claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.claim("userId", usuario.getId())
				.expirationTime(new Date(System.currentTimeMillis() + jwtConfiguration.getExpiration()))
				.build();
	}
	
	@SneakyThrows
	private KeyPair generateKeyPair() {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		return generator.genKeyPair();
	}
	
	public String encryptToken(SignedJWT signedJWT) throws JOSEException {
		log.info("Criptografando token...");
		DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());
		
		JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
				.contentType("JWT")
				.build();
		
		JWEObject jweObject = new JWEObject(header, new Payload(signedJWT));
		jweObject.encrypt(directEncrypter);
		log.info("Token criptografado com sucesso");
		return jweObject.serialize();
	}

}
