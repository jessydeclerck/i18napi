package com.pepito.i18napi.db.repository;

import com.pepito.i18napi.db.document.MapPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MapPositionRepository extends MongoRepository<MapPosition, Long> {
}
