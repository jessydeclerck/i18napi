package com.pepito.i18napi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiDto {

    private Integer id;

    private Integer nameId;

    private Integer categoryId;

}
