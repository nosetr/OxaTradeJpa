package com.nosetr.auth.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nosetr.auth.dto.AuthResponseDto;
import com.nosetr.auth.entity.UserEntity;
import com.nosetr.auth.repository.UserRepository;
import com.nosetr.auth.service.SecurityService;
import com.nosetr.auth.service.UserService;
import com.nosetr.auth.util.PBFDK2Encoder;
import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.util.exception.AuthException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Make handling with authentication and tokens generation.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

	//	private final UserService userService;
	private final PBFDK2Encoder passwordEncoder;
	private final UserRepository userRepository;

	// Values from config-file:
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Integer expirationInSeconds;
	@Value("${jwt.issuer}")
	private String issuer;

	/**
	 * Begin users login with values from {@link com.nosetr.auth.dto.AuthRequestDto
	 * AuthRequestDto} and return mapped {@link AuthResponseDto}.
	 * 
	 * @autor           Nikolay Osetrov
	 * @since           0.1.0
	 * @param  email    String
	 * @param  password String
	 * @return
	 * @see             UserService
	 * @see             AuthResponseDto
	 * @see             PBFDK2Encoder#matches(CharSequence, String)
	 */
	@Override
	public AuthResponseDto authenticate(String email, String password) {

		// Find UserEntity by email:
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(() -> new AuthException(ErrorEnum.INVALID_EMAIL_IS_REQUESTED));

		// Exception if user's account is not active:
		if (!user.isEnabled()) { throw new AuthException(ErrorEnum.USER_ACCOUNT_IS_DISABLED); }

		// Exception if password is invalid:
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new AuthException(ErrorEnum.INVALID_PASSWORD_IS_REQUESTED);
		}

		// Token generation:
		return generateToken(user).toBuilder()
				.userId(user.getId()) // add userId to claims
				.build();
	}

	/**
	 * generateToken #1
	 * <p>Build claims as HashMap and return generateToken #2.
	 * 
	 * @autor       Nikolay Osetrov
	 * @since       0.1.0
	 * @param  user UserEntity
	 * @return      AuthResponseDto
	 */
	private AuthResponseDto generateToken(UserEntity user) {
		Map<String, Object> claims = new HashMap<>() {
			private static final long serialVersionUID = 3877691385799795719L;
			{
				put("role", user.getUserRole());
				put("email", user.getEmail());
			}
		};
		// generateToken #2:
		return generateToken(
				claims, user.getId()
						.toString()
		);
	}

	/**
	 * generateToken #2
	 * 
	 * @autor          Nikolay Osetrov
	 * @since          0.1.0
	 * @param  claims  Map<String, Object>
	 * @param  subject UserId as String
	 * @return         AuthResponseDto
	 * @see            AuthResponseDto
	 */
	private AuthResponseDto generateToken(Map<String, Object> claims, String subject) {

		// Set expirationDate:
		Long expirationTimeInMillis = expirationInSeconds * 1000L;
		Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);

		// Set creations date:
		Date createdDate = new Date();

		// Encode jwt.secret from config-file:
		byte[] bytes = Decoders.BASE64.decode(
				Base64.getEncoder()
						.encodeToString(secret.getBytes())
		);

		String token = Jwts.builder()
				.claims()
				.add(claims)
				.issuer(issuer)
				.subject(subject)
				.issuedAt(createdDate)
				.id(
						UUID.randomUUID()
								.toString()
				)
				.expiration(expirationDate)
				.and()
				.signWith(Keys.hmacShaKeyFor(bytes), Jwts.SIG.HS256)
				.compact();

		// Build AuthResponseDto:
		return AuthResponseDto.builder()
				.token(token)
				.issuedAt(createdDate)
				.expiresAt(expirationDate)
				.build();
	}
}
