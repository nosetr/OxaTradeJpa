package com.nosetr.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nosetr.auth.entity.NewsthemeEntity;

/**
 * Repository for newstheme-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Repository
public interface NewsthemeRepository extends JpaRepository<NewsthemeEntity, Long> {

	Optional<NewsthemeEntity> findOneById(Long id);

//	List<NewsthemeEntity> findNewsthemesByNewsletterId(UUID emailId);
}
