package br.com.nkey.config.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nkey.domain.Usuario;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenConverter {

	@Autowired
	private JwtConfiguration jwtConfiguration;
	
	// @SneakyThrows
	// public String decryptToken(String encryptedToken) {
	// 	log.info("Descriptografando token...");
	// 	JWEObject jweObject = JWEObject.parse(encryptedToken);
	// 	DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());
	// 	jweObject.decrypt(directDecrypter);
	// 	log.info("Token descriptografado, returnando token assinado...");
	// 	return jweObject.getPayload().toSignedJWT().serialize();
	// }
	
	// @SneakyThrows
	// public void validateToken(String signedToken) {
	// 	log.info("Iniciando validacao da assinatura do token...");
	// 	SignedJWT signedJWT = SignedJWT.parse(signedToken);
	// 	log.info("Recuperando chave publica");
	// 	RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
	// 	log.info("Chave publica recuperada, validando assinatura...");
	// 	if(!signedJWT.verify(new RSASSAVerifier(publicKey))) {
	// 		throw new AccessDeniedException("Assinatura invalida!");
	// 	}
	// 	log.info("Assinatura valida, verificando tempo de expiracao do token");
	// 	if(signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date())) {
	// 		throw new AccessDeniedException("Token expirado!");
	// 	}
	// 	log.info("validacao completada");
	// }

	  /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public Usuario parseToken(String token) {
        // try {
            // Claims body = Jwts.parser()
			// 	.setSigningKey(jwtConfiguration.getPrivateKey())
			// 	.parseClaimsJws(token)
			// 	.getBody();

			// return Usuario.builder()
			// 	.id((String) body.get("userId"))
			// 	.email(body.getSubject())
			// 	.permissao((String) body.get("role"))
			// 	.build();

				DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtConfiguration.getPrivateKey()))
					.build()
					.verify(token);
				
				return Usuario.builder()
					.id(decodedJWT.getClaim("userId").asString())
					.email(decodedJWT.getSubject())
					.permissao(decodedJWT.getClaim("authorities").asString())
					.build();

        // } catch (JwtException | ClassCastException e) {
        //     return null;
        // }
    }
	
}
