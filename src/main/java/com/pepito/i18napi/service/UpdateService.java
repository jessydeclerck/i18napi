package com.pepito.i18napi.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepito.i18napi.db.document.Item;
import com.pepito.i18napi.db.document.MapPosition;
import com.pepito.i18napi.db.document.Npc;
import com.pepito.i18napi.db.document.PointOfInterest;
import com.pepito.i18napi.db.repository.ItemRepository;
import com.pepito.i18napi.db.repository.MapPositionRepository;
import com.pepito.i18napi.db.repository.NpcRepository;
import com.pepito.i18napi.db.repository.PointOfInterestRepository;
import com.pepito.i18napi.service.dto.NpcDto;
import com.pepito.i18napi.service.dto.PoiDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class UpdateService {

    private final ItemRepository itemRepository;
    private final MapPositionRepository mapPositionRepository;
    private final NpcRepository npcRepository;
    private final PointOfInterestRepository poiRepository;

    public void processFile(MultipartFile file, String filename) throws IOException {
        log.info("Starting update process...");
        switch (filename) {
            case "i18n":
                handlei18nFile(file);
                break;
            case "mapposition":
                handleMapPositionFile(file);
                break;
            case "npc":
                handleNpcFile(file);
                break;
            case "poi":
                handlePoiFile(file);
                break;
            default:
                break;
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

    private void handleNpcFile(MultipartFile file) throws IOException {
        log.info("Npc file processing starts");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(file.getInputStream())) {
            objectMapper.readValues(parser, NpcDto[].class).readAll().stream().flatMap(Stream::of).forEach(npcDto -> {
                Item item = itemRepository.findById(npcDto.getNameId()).get();
                Npc npc = Npc.builder().npcId(npcDto.getId()).label(item.getLabel()).build();
                npcRepository.save(npc);
            });
        }
    }

    private void handlePoiFile(MultipartFile file) throws IOException {
        log.info("Poi file processing starts");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(file.getInputStream())) {
            objectMapper.readValues(parser, PoiDto[].class).readAll().stream().flatMap(Stream::of).forEach(poiDto -> {
                Item item = itemRepository.findById(poiDto.getNameId()).get();
                PointOfInterest poi = PointOfInterest.builder().poiId(poiDto.getId()).label(item.getLabel()).build();
                poiRepository.save(poi);
            });
        }
    }

    private void updateItemData(String id, String text) {
        log.info("Upsert item {} ", id);
        Item itemToUpdate = Item.builder()
                .itemId(Integer.valueOf(id))
                .label(text).build();
        itemRepository.save(itemToUpdate);
    }

}
