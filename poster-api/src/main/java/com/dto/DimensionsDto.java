package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant les dimensions d'un poster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionsDto {
    private Double width;
    private Double height;
}
