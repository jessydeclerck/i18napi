package com.pepito.i18napi.db.repository;

import com.pepito.i18napi.db.document.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Integer> {
}
