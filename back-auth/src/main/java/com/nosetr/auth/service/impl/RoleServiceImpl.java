package com.nosetr.auth.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.nosetr.auth.entity.RoleEntity;
import com.nosetr.auth.enums.UserRoleEnum;
import com.nosetr.auth.repository.RoleRepository;
import com.nosetr.auth.service.RoleService;
import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.util.exception.EntityNotFoundException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Implementation of {@link RoleService} for users actions.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {

	RoleRepository roleRepository;

	/**
	 * Find users role by ID
	 * 
	 * @autor                          Nikolay Osetrov
	 * @since                          0.1.4
	 * @param  id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public RoleEntity getRoleById(Long id) throws EntityNotFoundException {
		return roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ErrorEnum.USER_ROLE_ID_NOT_FOUND, id));
	}

	/**
	 * Find users role by {@link UserRoleEnum}
	 * 
	 * @autor                          Nikolay Osetrov
	 * @since                          0.1.4
	 * @param  roleEnum
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public RoleEntity findByEnum(UserRoleEnum roleEnum) throws EntityNotFoundException {
		return roleRepository.findByName(roleEnum.getName())
				.orElseThrow(() -> new EntityNotFoundException(ErrorEnum.USER_ROLE_NAME_NOT_FOUND, roleEnum.getName()));
	}

	/**
	 * Get default roles from {@link UserRoleEnum} as Set<> for user registration.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return
	 */
	@Override
	public Set<RoleEntity> getDefaultRoles() {
		RoleEntity defaultRoleEntity = this.findByEnum(UserRoleEnum.DEFAULT);

		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		roleEntities.add(defaultRoleEntity);

		return roleEntities;
	}

}
