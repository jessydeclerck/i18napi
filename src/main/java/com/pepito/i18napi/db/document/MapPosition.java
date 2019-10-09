package com.pepito.i18napi.db.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapPosition {

    private Long id;

    private Integer posX;

    private Integer posY;

}
