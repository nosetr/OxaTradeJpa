package com.nosetr.auth.service;

import java.util.List;
import java.util.UUID;

import com.nosetr.auth.dto.UserDto;
import com.nosetr.auth.dto.UserRegisterDto;
import com.nosetr.auth.dto.UserUpdateDto;
import com.nosetr.library.exception.EntityNotFoundException;

/**
 * Service interface for users actions.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
public interface UserService {

	/**
	 * Create new user with UserRepository.
	 * 
	 * @autor             Nikolay Osetrov
	 * @since             0.1.0
	 * @param  userEntity UserEntity
	 * @return
	 */
	UserDto registerUser(UserRegisterDto userDto);

	/**
	 * Update user by ID
	 * 
	 * @autor          Nikolay Osetrov
	 * @since          0.1.0
	 * @param  id
	 * @param  userDto
	 * @return
	 */
	UserDto update(UUID id, UserUpdateDto userDto);

	/**
	 * Find user by ID
	 * 
	 * @autor     Nikolay Osetrov
	 * @since     0.1.0
	 * @param  id UUID
	 * @return    Mono<UserEntity>
	 */
	UserDto getUserById(UUID id) throws EntityNotFoundException;

	/**
	 * Find user by email
	 * 
	 * @autor        Nikolay Osetrov
	 * @since        0.1.0
	 * @param  email String
	 * @return       Mono<UserEntity>
	 */
	UserDto getUserByEmail(String email) throws EntityNotFoundException;

	/**
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Get all users
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.0
	 * @return Flux<UserEntity>
	 */
	List<UserDto> getAll();

	/**
	 * Delete user by ID
	 * 
	 * @autor     Nikolay Osetrov
	 * @since     0.1.0
	 * @param  id UUID
	 * @return    Mono<Void>
	 */
	void deleteById(UUID id);

	/**
	 * Delete all users
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.0
	 * @return Mono<Void>
	 */
	void deleteAll();

}
