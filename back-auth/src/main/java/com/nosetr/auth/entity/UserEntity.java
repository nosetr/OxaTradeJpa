package com.nosetr.auth.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.nosetr.auth.enums.OAuth2ProvidersEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(25)")
	private OAuth2ProvidersEnum provider; // 'google' or 'facebook', etc.

	@JoinTable(
			name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(
					name = "role_id"
			)
	)
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<RoleEntity> userRoles;

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
