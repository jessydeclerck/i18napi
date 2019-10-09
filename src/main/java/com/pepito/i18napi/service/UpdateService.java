package com.pepito.i18napi.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepito.i18napi.db.document.Item;
import com.pepito.i18napi.db.document.MapPosition;
import com.pepito.i18napi.db.repository.ItemRepository;
import com.pepito.i18napi.db.repository.MapPositionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Log4j2
@Service
public class UpdateService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MapPositionRepository mapPositionRepository;

    public void processFile(MultipartFile file, String filename) throws IOException {
        log.info("Starting update process...");
        if ("i18n".equals(filename)) {
            handlei18nFile(file);
        } else if ("mapposition".equals(filename)) {
            handleMapPositionFile(file);
        }
    }

    private void handlei18nFile(MultipartFile file) throws IOException {
        log.info("i18n file processing starts");
        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(file.getInputStream())) {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                if ("texts".equals(fieldName)) {
                    parser.nextToken();
                    while (parser.nextToken() != JsonToken.END_OBJECT) {
                        parser.nextToken();
                        String id = parser.getCurrentName();
                        String text = parser.getText();
                        this.updateItemData(id, text);
                    }
                }
            }
        }
        log.info("i18n processing done");
    }

    private void handleMapPositionFile(MultipartFile file) throws IOException {
        log.info("Map Position file processing starts");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(file.getInputStream())) {
            objectMapper.readValues(parser, MapPosition[].class).readAll().stream().flatMap(Stream::of).forEach(mapPositionRepository::save);
        }
        log.info("Map Position processing done");
    }

    private void updateItemData(String id, String text) {
        log.info("Upsert item {} ", id);
        Item itemToUpdate = Item.builder()
                .itemId(Integer.valueOf(id))
                .itemText(text).build();
        itemRepository.save(itemToUpdate);
    }

}
