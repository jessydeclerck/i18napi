package com.pepito.i18napi.db.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Npc {

    private Integer npcId;

    private String label;

}
