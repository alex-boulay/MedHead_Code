package config;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import java.security.Key;
//import java.security.KeyPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ocal.medhead.service.KeyService;

@Component
@Getter
public class JWTGenerator {
	
	private final KeyService keyService;
	
	private Key key;
	
	@Autowired
	public JWTGenerator(KeyService keyService) {
		this.keyService = keyService;
		this.key = keyService.readSecretKeyFromFile();
	}
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPRITATION);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt( new Date())
				.setExpiration(expireDate)
				.signWith(getKey(),SignatureAlgorithm.HS512)
				.compact();
		return token;
	}
	
    public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        List<?> roles = (List<?>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (roles != null) {
            for (Object role : roles) {
                if (role instanceof String) {
                    authorities.add(new SimpleGrantedAuthority((String) role));
                }
            }
        }

        return authorities;
    }
    
	public String getUsernameFromJWT(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect");
		}
	}
	
	public Key getKey() {
		return key;
	}

}
