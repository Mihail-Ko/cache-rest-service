package com.example.cacherestservice.controller;

import com.example.cacherestservice.entity.ReaderEntity;
import com.example.cacherestservice.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @PostMapping
    public ResponseEntity addReader(@RequestBody ReaderEntity reader) {
        try {
            readerService.addReader(reader);
            return ResponseEntity.ok("Читатель сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getReaders() {
        try {
            return ResponseEntity.ok(readerService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneReader(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(readerService.getOne(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Читатель не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReader(@PathVariable Long id) {
        try {
            return ResponseEntity.ok("Удален читатель с id "+readerService.delete(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Читатель не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity updateReader(@RequestBody ReaderEntity reader) {
        try {
            return ResponseEntity.ok(readerService.update(reader));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Читатель не найден");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

