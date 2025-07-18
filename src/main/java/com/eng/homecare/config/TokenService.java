package com.eng.homecare.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eng.homecare.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getUserId())
                .withClaim("name", user.getName())
                .withClaim("Type", user.getTypeUser().toString())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API HomeCare")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);


            return Optional.of(
                    JWTUserData
                            .builder()
                            .id(jwt.getClaim("userId").asLong())
                            .name(jwt.getClaim("name").asString())
                            .email(jwt.getSubject())
                            .build());

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }


    }
}
