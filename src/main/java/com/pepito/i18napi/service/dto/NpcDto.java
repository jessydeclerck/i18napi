package com.pepito.i18napi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class NpcDto {

    private Integer id;

    private Integer nameId;

}
