package com.nosetr.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nosetr.auth.dto.EmailDto;
import com.nosetr.auth.dto.NewsletterDto;
import com.nosetr.auth.entity.NewsletterEntity;
import com.nosetr.auth.entity.NewsthemeEntity;
import com.nosetr.auth.mapper.NewsletterMapper;
import com.nosetr.auth.repository.NewsletterRepository;
import com.nosetr.auth.repository.NewsthemeRepository;
import com.nosetr.auth.service.NewsletterService;
import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.exception.EntityAlreadyExistsException;
import com.nosetr.library.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of NewsletterService for newsletters actions.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

	private final NewsletterRepository newsletterRepository;
	private final NewsthemeRepository newsthemeRepository;
	private final NewsletterMapper newsletterMapper;

	@Override
	public NewsletterDto saveEmail(EmailDto emailDto) {
		String email = emailDto.getEmail();
		// TODO Default theme if not exist:
		Long themeId = (emailDto.getNewstheme() == null) ? 1L : emailDto.getNewstheme();

		Optional<NewsletterEntity> optionalEmail = newsletterRepository.findByEmail(email);
		if (optionalEmail.isPresent()) {
			throw new EntityAlreadyExistsException(ErrorEnum.NEWS_EMAIL_ALREADY_EXISTS, email);
		}

		NewsletterEntity newsletterEntity = new NewsletterEntity();
		newsletterEntity.setEmail(email);
		newsletterEntity.setEnabled(false);
		newsletterEntity.setLastUpdate(LocalDateTime.now());

		NewsthemeEntity newsthemeEntity = newsthemeRepository.findOneById(themeId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorEnum.NEWS_THEME_NOT_FOUND, themeId));

//		newsletterEntity.addTheme(newsthemeEntity);// TODO Many-To-Many
		log.debug("IN saveEmail - newsletterEntity: {}", newsletterEntity);

		NewsletterEntity savedEmail = newsletterRepository.save(newsletterEntity);
		log.debug("IN saveEmail - email: {} created", savedEmail);

		return newsletterMapper.map(savedEmail);
	}

	@Override
	public NewsletterDto updateEmail(NewsletterDto newsletterDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmail(String email) {
		// TODO Auto-generated method stub
	}

}
