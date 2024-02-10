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

@Entity
@Table(name = "items")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

	@Id
	@UuidGenerator
	private UUID id;

	private String name;
	private float price;
	private boolean priceWithVat;
	private short vat;
	private short itemUnit;
	private float weight;
	private short weightUnit;
	private UUID parent;
	private String gtin;
	private String mpn;
	private String description;
	private String memo;
	private boolean enabled;
	private boolean inArchives;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
