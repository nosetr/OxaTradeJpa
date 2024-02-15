package com.nosetr.auth.service;

import java.util.Set;

import com.nosetr.auth.entity.RoleEntity;
import com.nosetr.auth.enums.UserRoleEnum;
import com.nosetr.library.exception.EntityNotFoundException;

/**
 * Service interface for users with roles actions.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
public interface RoleService {

	/**
	 * Find role by ID
	 * 
	 * @autor                          Nikolay Osetrov
	 * @since                          0.1.4
	 * @param  id
	 * @return
	 * @throws EntityNotFoundException
	 */
	RoleEntity getRoleById(Long id) throws EntityNotFoundException;

	/**
	 * Find role by {@link UserRoleEnum}
	 * 
	 * @autor                          Nikolay Osetrov
	 * @since                          0.1.4
	 * @param  roleEnum
	 * @return
	 * @throws EntityNotFoundException
	 */
	RoleEntity findByEnum(UserRoleEnum roleEnum) throws EntityNotFoundException;

	/**
	 * Get default roles from {@link UserRoleEnum} as Set<> for user registration.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	Set<RoleEntity> getDefaultRoles();

}
