package com.nosetr.auth.service;

import com.nosetr.auth.dto.EmailDto;
import com.nosetr.auth.dto.NewsletterDto;

/**
 * Service interface for newsletters actions.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
public interface NewsletterService {

	/**
	 * Save new email.
	 * 
	 * @autor                Nikolay Osetrov
	 * @since                0.1.0
	 * @param  newsletterDto
	 * @return
	 */
	NewsletterDto saveEmail(EmailDto emailNewsletterDto);

	/**
	 * Update exists email.
	 * 
	 * @autor                Nikolay Osetrov
	 * @since                0.1.0
	 * @param  newsletterDto
	 * @return
	 */
	NewsletterDto updateEmail(NewsletterDto newsletterDto);

	/**
	 * Delete email.
	 * 
	 * @autor        Nikolay Osetrov
	 * @since        0.1.0
	 * @param  email
	 * @return
	 */
	void deleteEmail(String email);
}
