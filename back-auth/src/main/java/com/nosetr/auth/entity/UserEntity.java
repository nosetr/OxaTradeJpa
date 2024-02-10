package com.nosetr.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.nosetr.auth.enums.OAuth2ProvidersEnum;
import com.nosetr.auth.enums.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity for users-table
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@Entity
@Table(name = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@UuidGenerator
	private UUID id;

	private String email;
	private String password;
	private OAuth2ProvidersEnum provider; // 'google' or 'facebook', etc.
	private UserRoleEnum userRole;
	private String title;
	private String firstName;
	private String lastName;
	private boolean enabled;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ToString.Include(name = "password")
	private String maskPassword() {
		return "********";
	}
}
