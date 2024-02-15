package com.nosetr.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for countries-table
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.1
 */
@Entity
@Table(name = "countries")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CountriesEntity {

    @Id
    private String countryCode;
    private String countryName;
    private String phonePrefix;
    private boolean enabled; // if usable for user

}
