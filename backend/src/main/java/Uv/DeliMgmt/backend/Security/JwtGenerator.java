package Uv.DeliMgmt.backend.Security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {

    public String generateJwt(Authentication auth) {
        String username = auth.getName();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)  // Añadir roles al token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }


    public String usernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token");
        } catch (SignatureException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT signature validation failed");
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token");
        }
    }
}
