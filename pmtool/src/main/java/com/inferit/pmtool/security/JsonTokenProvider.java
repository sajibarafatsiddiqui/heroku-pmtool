package com.inferit.pmtool.security;
import com.inferit.pmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.inferit.pmtool.security.SecurityConstants.SECURITY;
import static com.inferit.pmtool.security.SecurityConstants.TOKEN_SESSION;
@Component
public class JsonTokenProvider {
    //Generate Token
    public String generateToken(Authentication authentication){
        User user= (User) authentication.getPrincipal();
        Date now= new Date(System.currentTimeMillis());
        Date expiration=new Date(now.getTime()+ TOKEN_SESSION);
        String userId= Long.toString(user.getId());
        Map<String,Object> claims= new HashMap();
        claims.put("id",userId);
        claims.put("userName",user.getUserName());
        claims.put("fullName",user.getFullName());
        System.out.println(user.getUserName() + "generateToken");
        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,SECURITY).compact();


    }
    //Validate Token
public boolean validateToken(String token){
    try{
        Jwts.parser().setSigningKey(SECURITY).parseClaimsJws(token);
        return true;
    }catch (IllegalArgumentException e){
      System.out.println("Wrong Token");

    }catch(MalformedJwtException e) {
        System.out.println("The token is malformed");
    }catch(ExpiredJwtException e){
       System.out.println("The token has been expired. Try again!");

    }catch(UnsupportedJwtException e){
      System.out.println("The token is in unsupported format");
    }catch(SignatureException e){
       System.out.println("The signature is wrong");
    }
    return false;
}


public Long getUserIdFromToken(String token){

            Claims claims=Jwts.parser().setSigningKey(SECURITY).parseClaimsJws(token).getBody();
    System.out.println(claims);
            String id=(String) claims.get("id");
            return Long.parseLong(id);

}




    //
}
