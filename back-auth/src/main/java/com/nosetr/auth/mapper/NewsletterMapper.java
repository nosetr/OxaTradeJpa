package com.nosetr.auth.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nosetr.auth.dto.NewsletterDto;
import com.nosetr.auth.entity.NewsletterEntity;

/**
 * Mapper between NewsletterDto and NewsletterEntity / NewsthemeEntity
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.2
 */
@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface NewsletterMapper {

//	@Mapping(source = "newsthemen.theme_name", target = "themeName")
//	@Mapping(source = "newsthemen.memo", target = "themeMemo")
	@Mapping(target = "themeMemo", ignore = true)
	@Mapping(target = "themeName", ignore = true)
	NewsletterDto map(NewsletterEntity entity);

	@InheritInverseConfiguration
	@Mapping(target = "newsthemen", ignore = true)
	NewsletterEntity map(NewsletterDto dto);

//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "enabled", ignore = true)
//	@Mapping(target = "lastUpdate", ignore = true)
//	@Mapping(target = "newsthemen", ignore = true)
//	@Mapping(target = "newsthemen", qualifiedByName = "newsthemeDtoToNewsthemeEntitySet")
//	NewsletterEntity map(EmailDto emailDto);
	
//	@Named("newsthemeDtoToNewsthemeEntitySet")
//  default Set<NewsthemeEntity> newsthemenDtoToNewsthemeEntitySet(Set<NewsthemeDto> newsthemenDtoSet) {
//      return newsthemenDtoSet.stream()
//              .map(this::newsthemenDtoToNewsthemeEntity)
//              .collect(Collectors.toSet());
//  }
//	
//	@Named("newsthemeDtoToNewsthemeEntity")
//	NewsthemeEntity newsthemenDtoToNewsthemeEntity(NewsthemeDto newsthemenDto);
}
