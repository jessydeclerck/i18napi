package com.pepito.i18napi.db.repository;

import com.pepito.i18napi.db.document.Npc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NpcRepository extends MongoRepository<Npc, Integer> {
}
