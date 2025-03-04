package org.example.usermicroservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKey; // Injected SecretKey

    // Token validity period (e.g., 10 hours)
    private final long JWT_TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

    public JwtTokenUtil(SecretKey secretKey) {
        this.secretKey = secretKey; // Use the injected SecretKey
    }

    // Generate a JWT token
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add role to claims
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Add custom claims
                .setSubject(subject) // Subject (username)
                .setIssuedAt(new Date()) // Issue date
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Expiration
                .signWith(secretKey, SignatureAlgorithm.HS256) // Sign with the injected SecretKey
                .compact(); // Generate token
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // Use parserBuilder instead of the deprecated parser
                .setSigningKey(secretKey) // Use the injected SecretKey
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the JWT token
                .getBody(); // Get the claims from the token
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
