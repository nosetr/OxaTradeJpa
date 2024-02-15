package com.nosetr.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nosetr.auth.dto.AuthRequestDto;
import com.nosetr.auth.dto.AuthResponseDto;
import com.nosetr.auth.dto.UserDto;
import com.nosetr.auth.dto.UserRegisterDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Interface for actions with users authentication.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Tag(name = "Authentication_V1", description = "APIs for users authentication and registration")
@RequestMapping("/api/v1/auth")
public interface AuthRestApiV1 {

	/**
	 * Users registration action with requested body.
	 * 
	 * @autor          Nikolay Osetrov
	 * @since          0.1.4
	 * @param  userDto UserDto
	 * @return
	 * @see            UserDto
	 */
	@Operation(
			summary = "Register new user", description = "Password need:\n"
					+ " * at least 8 characters and at most 100 chars,\n"
					+ " * at least one upper-case character,\n"
					+ " * at least one lower-case character,\n"
					+ " * at least one digit character,\n"
					+ " * at least one symbol (special character)\n"
					+ " * and no whitespace", tags = { "users_tag", "post_tag" }
	)
	@ApiResponses(
		{
				@ApiResponse(
						responseCode = "200", content = {
								@Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json") }
				),
				@ApiResponse(
						responseCode = "500", content = { @Content(
								schema = @Schema()
						) }
				)
		}
	)
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto);

	/**
	 * Users login action.
	 * <p> Begin of authentication with email and password.
	 * 
	 * @autor                 Nikolay Osetrov
	 * @since                 0.1.4
	 * @param  authRequestDto AuthRequestDto
	 * @return
	 * @see                   AuthRequestDto
	 */
	@Operation(
			summary = "Login with email and password. Create response with token and additional info.", tags = { "users_tag",
					"post_tag" }
	)
	@ApiResponses(
		{
				@ApiResponse(
						responseCode = "200", content = {
								@Content(schema = @Schema(implementation = AuthResponseDto.class), mediaType = "application/json") }
				),
				@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
				@ApiResponse(
						responseCode = "500", content = { @Content(
								schema = @Schema()
						) }
				)
		}
	)
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto);
}
