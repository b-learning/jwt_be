package com.example.jwt.jwt;

import com.example.jwt.dto.UserSecurity;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class TokenHandler {

    private final String SECRET_KEY = "VLXX";
    // 1s => 1000ms
    private final long EXPIRATION = 1 * 60 * 60 * 1000;

    // create token (encode)
    public String generateToken(UserSecurity userSecurity){
        Date now = new Date(); // get current time
        Date expirationDate = new Date(now.getTime() + EXPIRATION);

        String token = Jwts.builder()
                .setSubject(userSecurity.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }

    // get info from token (decode)

    // validate token

}
