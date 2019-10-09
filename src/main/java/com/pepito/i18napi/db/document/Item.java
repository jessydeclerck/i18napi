package com.pepito.i18napi.db.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Item {

    @Id
    private Integer itemId;

    private String itemText;

}
