package com.nosetr.auth.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for news theme.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Data
@Builder
public class NewsthemeDto {

	private Long id;
	private String themeName;
	private String memo;
}
