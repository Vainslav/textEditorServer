package javagnomes.texteditorserver.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${tokenKey}")
    private String tokenKey;

    public String createToken(List<String> allowedServerHosts) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withClaim("host", allowedServerHosts)
                .withExpiresAt(Instant.now().plusSeconds(36000 * 24))
                .sign(Algorithm.HMAC512(tokenKey));
    }

    public boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(tokenKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public Claim getClaimFromToken(String token, String item) {
        if (!verifyToken(token)){
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC512(tokenKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        final DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(item);
    }
}
