package com.nosetr.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nosetr.auth.entity.NewsletterEntity;

/**
 * Repository for newsletter-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Repository
public interface NewsletterRepository extends JpaRepository<NewsletterEntity, UUID> {

	Optional<NewsletterEntity> findByEmail(@Param("email") String email);

//	List<NewsletterEntity> findNewslettersByNewsthemeId(Long themeId);
}
