package com.example.cacherestservice.controller;

import com.example.cacherestservice.entity.ReaderEntity;
import com.example.cacherestservice.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @PostMapping
    public ResponseEntity addReader(@RequestBody ReaderEntity reader) {
        readerService.addReader(reader);
        return ResponseEntity.ok("Читатель сохранен");
    }

    @GetMapping
    public ResponseEntity getReaders() {
        return ResponseEntity.ok(readerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneReader(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReader(@PathVariable Long id) {
        return ResponseEntity.ok("Удален читатель с id "+readerService.delete(id));
    }

    @PutMapping()
    public ResponseEntity updateReader(@RequestBody ReaderEntity reader) {
        return ResponseEntity.ok(readerService.update(reader));
    }
}

