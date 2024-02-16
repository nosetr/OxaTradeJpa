package com.nosetr.auth.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.nosetr.auth.controller.UserRestV1Controller;
import com.nosetr.auth.dto.AuthRequestDto;
import com.nosetr.auth.dto.AuthResponseDto;
import com.nosetr.auth.dto.UserDto;
import com.nosetr.auth.dto.UserRegisterDto;
import com.nosetr.auth.dto.UserUpdateDto;
import com.nosetr.auth.mapper.UserMapper;
import com.nosetr.auth.security.CustomPrincipal;
import com.nosetr.auth.service.SecurityService;
import com.nosetr.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for actions with users authentication.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@RestController
@RequiredArgsConstructor
public class UserRestV1ControllerImpl implements UserRestV1Controller {

	private final SecurityService securityService;
	private final UserService userService;
	private final UserMapper userMapper;

	/**
	 * Users registration action with requested body.
	 * TODO confirmations link on email must be send before account is enabled.
	 * 
	 * @autor                  Nikolay Osetrov
	 * @since                  0.1.0
	 * @param  userRegisterDto
	 * @return
	 */
	@Override
	public ResponseEntity<UserDto> register(@Valid UserRegisterDto userRegisterDto) {
		return ResponseEntity.ok(userService.registerUser(userRegisterDto));
	}

	/**
	 * Users login action.
	 * <p> Begin of authentication with email and password.
	 * 
	 * @autor                 Nikolay Osetrov
	 * @since                 0.1.0
	 * @param  authRequestDto AuthRequestDto
	 * @return
	 * @see                   AuthRequestDto
	 */
	@Override
	public ResponseEntity<AuthResponseDto> login(@Valid AuthRequestDto authRequestDto) {

		// Authenticate user and retrieve token details synchronously:
		AuthResponseDto authResponseDto = securityService
				.authenticate(authRequestDto.getEmail(), authRequestDto.getPassword());

		// Return ResponseEntity with the created AuthResponseDto:
		return ResponseEntity.ok(authResponseDto);
	}

	/**
	 * User can update himself if login.
	 * 
	 * @autor                 Nikolay Osetrov
	 * @since                 0.1.0
	 * @param  userDto
	 * @param  authentication
	 * @return
	 */
	@Override
	public ResponseEntity<UserDto> selfUpdate(@Valid UserUpdateDto userDto, Authentication authentication) {
		
		CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
		UserDto user = userService.update(customPrincipal.getId(), userDto);
		return ResponseEntity.ok(user);
	}

	/**
	 * Get information about himself when user is on login.
	 * 
	 * @autor                 Nikolay Osetrov
	 * @since                 0.1.0
	 * @param  authentication
	 * @return
	 */
	@Override
	public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
		
		CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
		UserDto userById = userService.getUserById(customPrincipal.getId());
		return ResponseEntity.ok(userById);
	}

}
