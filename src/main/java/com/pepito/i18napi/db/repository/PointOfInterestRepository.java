package com.pepito.i18napi.db.repository;

import com.pepito.i18napi.db.document.PointOfInterest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointOfInterestRepository extends MongoRepository<PointOfInterest, Integer> {
}
