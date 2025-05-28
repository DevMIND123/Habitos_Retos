package com.retochimba.habitos.habitos_retos.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String extraerEmail(String token) {
        try {
            System.out.println("🛡️ Token recibido: " + token);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            return Jwts.parser()
                    .verifyWith(getSigningKey()) 
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (MalformedJwtException e) {
            System.err.println("❌ Token malformado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error procesando token: " + e.getMessage());
        }

        return null;
    }
}
