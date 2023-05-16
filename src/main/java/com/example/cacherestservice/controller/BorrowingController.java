package com.example.cacherestservice.controller;

import com.example.cacherestservice.entity.BorrowingEntity;
import com.example.cacherestservice.model.BorrowingModel;
import com.example.cacherestservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping
    public ResponseEntity addBorrowing(@RequestBody BorrowingModel borrowing) {
        try {
            borrowingService.addBorrowing(borrowing);
            return ResponseEntity.ok("Выдача сохранена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getBorrowings() {
        try {
            return ResponseEntity.ok(borrowingService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBorrowing(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(borrowingService.getOne(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Выдача не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBorrowing(@PathVariable Long id) {
        try {
            return ResponseEntity.ok("Удалена выдача с id "+borrowingService.delete(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Выдача не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReader(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(borrowingService.update(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Выдача не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
