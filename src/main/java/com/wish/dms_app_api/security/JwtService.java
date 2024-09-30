package com.wish.dms_app_api.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

	private String secretKey="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	 //   SecretKey key = Jwts.SIG.HS256.key().build();
//	SecretKey key= Jwts.SIG.HS256.key().build();
	
//	public JwtService() {
//        try {
//            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGenerator.generateKey();
//
//            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
    private SecretKey getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T>claimsResolver) {
		Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
//		return extractClaim(token, Claims.getSubject);
//		return Jwts.parser().verifyWith(key).parse(token).getBody();
		return Jwts.parser()
		.verifyWith(getKey())
		.build()
		.parseSignedClaims(token)
		.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		
		final String username= extractUsername(token);
		
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExp(token).before(new Date());
	}

	private Date extractExp(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public String generateToken(String username) {


		System.out.println(getKey().getFormat().getBytes());
		
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*7))
				.signWith(getKey())
				.compact();
	}


	public String generateRefreshToken(String username)
	{
		return Jwts.builder()

				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getKey())
				.compact();
	}


	public boolean isValidToken(String refreshToken, String username) {
		final String tokenUsername= extractUsername(refreshToken);


		return (tokenUsername.equals(username) && !isTokenExpired(refreshToken));
	}
}
