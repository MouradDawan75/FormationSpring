package fr.dawan.training.tools;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * 3 Dépendences à ajouter dans pom.xml
 * 
 * <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

La clé secrete définie dans application.properties doit contenir au minimum 56 caractères (chaine de 256 bits)


 * 
 * 
 */

@Component
public class JwtTokenTool {
	
	// durée de validité du token
		public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60; // 1 jours - en secondes

		// Lecture de ce paramtère à partir de application.properties
		@Value("${jwt.secret}")
		private String secret;

		public String getUsernameFromToken(String token) {
			return getClaimFromToken(token, Claims::getSubject);
		}

		public Date getIssuedAtDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getIssuedAt);
		}

		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}

		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}

		// Claims: terme qui désigne les détails du user
		public Claims getAllClaimsFromToken(String token) {
			return Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
		}

		public boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}

		public boolean ignoreTokenExpiration(String token) {
			return false;
		}

		public String doGenerateToken(Map<String, Object> claims, String subject) {
			return Jwts.builder()
					.claims(claims)
					.subject(subject)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(getKey()).compact();

		}

		public boolean canTokenBeRefreshed(String token) {
			return (!isTokenExpired(token) || ignoreTokenExpiration(token));
		}
		//Méthode de génération de clé pour la signature du Token
		public SecretKey getKey() {
			byte[] bytes = Decoders.BASE64.decode(secret);
			return Keys.hmacShaKeyFor(bytes);
			
		}

}
