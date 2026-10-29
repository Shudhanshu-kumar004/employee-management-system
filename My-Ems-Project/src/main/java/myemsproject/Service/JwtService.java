package myemsproject.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import myemsproject.enums.Role;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService{

    private static final String SECRET_KEY = "my-ems-project";
    private static final Long EXPIRATION_TIME = 15 * 60 * 10000L;

    public String generateToken(String emailId, Role role){
        return JWT.create()
                .withSubject(emailId)
                .withClaim("role", role.name())   //.name() is a method of every Java enum. It gives you the exact name of the enum constant as a String.
                                                         //Role.ADMIN,    role.name()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String validateTokenAndRetriveSubject (String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }

}
