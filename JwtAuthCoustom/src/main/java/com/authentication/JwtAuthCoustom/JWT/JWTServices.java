package com.authentication.JwtAuthCoustom.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.authentication.JwtAuthCoustom.DTO.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTServices {
	private static final Logger logger = LoggerFactory.getLogger(JWTServices.class);

	private static final String secretKey = "nDj5I1/6YWE8fK82U8Bf8HJgTKDSk5xx/DtVkqfetbwsgaZqLuAfFhRZI5wsmwAt0fcUCOfExA2Rht/bomB0aA==";
//	 Keys.secretKeyFor(SignatureAlgorithm.HS512)

	@Value("${jwt.expirationMs}")
	private long expirationMs;

	public String generateToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(userDetails, claims);
	}

	@SuppressWarnings("deprecation")
	private String createToken(CustomUserDetails customUser, Map<String, Object> additionalClaims) {
		return Jwts.builder().setSubject(customUser.getUsername()).claim("customUser", customUser)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60000000))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public boolean validateToken(String token, CustomUserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {
		try {
			return extractClaim(token, Claims::getSubject);
		} catch (ExpiredJwtException e) {
			logger.warn("JWT token expired: {}", e.getMessage());

			return null;
		} catch (Exception e) {
			logger.error("Error extracting username from token: {}", e.getMessage());
			return null;
		}
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		try {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		} catch (ExpiredJwtException e) {
			logger.warn("JWT token expired: {}", e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Error extracting claim from token: {}", e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public Claims extractAllClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			logger.warn("JWT token expired: {}", e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Error extracting all claims from token: {}", e.getMessage());
			return null;
		}
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
