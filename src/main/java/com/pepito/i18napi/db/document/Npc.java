package com.pepito.i18napi.db.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Npc {

    private Integer npcId;

    private String label;

}
