package com.nosetr.auth.security;

import java.util.Base64;
import java.util.Date;

import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.util.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Verification of users token
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@RequiredArgsConstructor
public class JwtHandler {

	private final String secret;

	/**
	 * Make result of verification.
	 * 
	 * @autor Nikolay Osetrov
	 * @since 0.1.0
	 */
	@AllArgsConstructor
	public static class VerificationResult {
		public Claims claims;
		public String token;
	}

	/**
	 * Begin to check the given token.
	 * 
	 * @autor              Nikolay Osetrov
	 * @since              0.1.0
	 * @param  accessToken BearerToken extracted from HttpHeaders.AUTHORIZATION
	 * @return             Mono<VerificationResult> or UnauthorizedException
	 */
	public VerificationResult check(String accessToken) {

		try {
			Claims claims = getClaimsFromToken(accessToken);
			final Date expirationDate = claims.getExpiration();

			// if expirationDate is over, then throw exception
			if (expirationDate.before(new Date())) { throw new RuntimeException(ErrorEnum.TOKEN_IS_EXPIRED.getMessage()); }

			return new VerificationResult(claims, accessToken);
		} catch (RuntimeException e) {
			throw new UnauthorizedException(ErrorEnum.TOKEN_IS_EXPIRED);
		}
	}

	/**
	 * Take claims from given token with secret.
	 * 
	 * @autor        Nikolay Osetrov
	 * @since        0.1.0
	 * @param  token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {

		byte[] bytes = Decoders.BASE64.decode(
				Base64.getEncoder()
						.encodeToString(secret.getBytes())
		);

		return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(bytes))
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
