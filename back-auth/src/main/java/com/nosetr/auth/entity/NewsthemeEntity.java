package com.nosetr.auth.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * Entity for newstheme-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Entity
@Table(name = "newstheme")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewsthemeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String themeName;
	private String memo;

	/*
	 * The many-to-many relationship to NewsletterEntity
	 */
	@Builder.Default
	@ManyToMany(mappedBy = "newsthemen", fetch = FetchType.LAZY)
	private Set<NewsletterEntity> emails = new HashSet<>();

}