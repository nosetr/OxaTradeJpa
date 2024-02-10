package com.nosetr.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nosetr.auth.entity.NewsthemaEntity;

/**
 * Repository for newsthema-table.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Repository
public interface NewsthemaRepository extends JpaRepository<NewsthemaEntity, Long> {

	Optional<NewsthemaEntity> findOneById(Long id);

//	List<NewsthemaEntity> findNewsthemesByNewsletterId(UUID emailId);
}
