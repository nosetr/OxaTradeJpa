package com.nosetr.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for addresses-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.1
 */
@Entity
@Table(name = "addresses")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

	@Id
	@UuidGenerator
	private UUID id;
	private Long orgId;
	private String titleName;
	private String aliasName;
	private String streetName;
	private String houseName;
	private String districtName;
	private String cityName;
	private String zipName;
	private String stateName;
	private String countryCode;
	private String email;
	private String phone;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private String memo;

}
