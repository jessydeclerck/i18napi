package com.pepito.i18napi.controller;

import com.pepito.i18napi.db.document.Item;
import com.pepito.i18napi.db.document.MapPosition;
import com.pepito.i18napi.db.repository.ItemRepository;
import com.pepito.i18napi.db.repository.MapPositionRepository;
import com.pepito.i18napi.service.UpdateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Controller
public class I18nController {

    @Autowired
    private UpdateService updateService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MapPositionRepository mapPositionRepository;

    @GetMapping("/update")
    public String updatePage() {
        return "update";
    }

    @PostMapping("/update/{filename}")
    public ResponseEntity<String> startUpdate(@RequestParam("file") MultipartFile file, @PathVariable String filename) {
        CompletableFuture.runAsync(() -> {
            try {
                updateService.processFile(file, filename);
            } catch (IOException e) {
                log.error("Error while processing file", e);
            }
        });
        return ResponseEntity.ok("Update process has started.");
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Integer id) {
        return ResponseEntity.ok(itemRepository.findById(id).orElseThrow());
    }

    @GetMapping("/mapposition/{id}")
    public ResponseEntity<MapPosition> getMapPosition(@PathVariable Long id){
        return ResponseEntity.ok(mapPositionRepository.findById(id).orElseThrow());
    }

}
