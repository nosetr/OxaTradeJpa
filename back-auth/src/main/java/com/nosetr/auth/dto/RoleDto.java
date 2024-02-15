package com.nosetr.auth.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.nosetr.auth.enums.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

	private Long id;
	private UserRoleEnum name;
	private Set<UserDto> users;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
