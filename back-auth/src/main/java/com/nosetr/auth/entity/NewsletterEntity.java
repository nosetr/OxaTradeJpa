package com.nosetr.auth.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

/**
 * Entity for newsletter-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.1
 */
@Entity
@Table(name = "newsletter")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterEntity {

	@Id
	@UuidGenerator
	private UUID id;

	private String email;
	private boolean enabled;

	@UpdateTimestamp
	private LocalDateTime lastUpdate;

	/*
	 * The many-to-many relationship to NewsthemaEntity
	 */
	@Builder.Default
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(
			name = "newsletter_newsthema", joinColumns = { @JoinColumn(name = "email_id") }, inverseJoinColumns = {
					@JoinColumn(name = "thema_id") }
	)
	private Set<NewsthemaEntity> newsthemen = new HashSet<>();

	/**
	 * Add theme by many-to-many.
	 * 
	 * @autor           Nikolay Osetrov
	 * @since           0.1.2
	 * @param themaMono
	 * @see             https://manerajona.medium.com/mapping-bidirectional-object-associations-using-mapstruct-ce49b1857604
	 */
	//	public void addTheme(NewsthemaEntity thema) {
	//		if (this.newsthemen == null) this.newsthemen = new HashSet<>();
	//		this.newsthemen.add(thema);
	//		thema.getEmails()
	//				.add(this);
	//	}

	/**
	 * Remove theme by many-to-many.
	 * 
	 * @autor         Nikolay Osetrov
	 * @since         0.1.2
	 * @param themeId
	 */
	public void removeTheme(Long themeId) {
		this.newsthemen.removeIf(
				theme -> theme.getId()
						.equals(themeId)
		);
	}
}
