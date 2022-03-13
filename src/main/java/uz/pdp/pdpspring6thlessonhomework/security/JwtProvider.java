package uz.pdp.pdpspring6thlessonhomework.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final long expiredTime = 1000*60*60*24;
    private static final  String secretKey = "PDPuzSpringLessons";

    public String generateToken(String username, Role role){
       Date expireDate =  new Date(System.currentTimeMillis()+expiredTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", role)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;

    }

    public String getEmailFromToken(String token){
        try {


            String email = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJwt(token)
                    .getBody()
                    .getSubject();
            return email;
        }catch (Exception e){
            return  null;
        }
    }
}
