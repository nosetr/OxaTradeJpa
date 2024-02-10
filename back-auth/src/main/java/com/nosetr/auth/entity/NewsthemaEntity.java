package com.nosetr.auth.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for newsthema-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Entity
@Table(name = "newsthema")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewsthemaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String themaName;
	private String memo;

	/*
	 * The many-to-many relationship to NewsletterEntity
	 */
	@Builder.Default
	@ManyToMany(mappedBy = "newsthemen")
	private Set<NewsletterEntity> emails = new HashSet<>();

}