package com.example.restservice.controller;


import com.example.restservice.model.BorrowingModel;
import com.example.restservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping
    public ResponseEntity addBorrowing(@RequestBody BorrowingModel borrowing) {
        borrowingService.addBorrowing(borrowing);
        return ResponseEntity.ok("Выдача сохранена");
    }

    @GetMapping
    public ResponseEntity getBorrowings() {
        return ResponseEntity.ok(borrowingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBorrowing(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBorrowing(@PathVariable Long id) {
        return ResponseEntity.ok("Удалена выдача с id " + borrowingService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReader(@PathVariable Long id) {
        return ResponseEntity.badRequest().body("Выдача не найдена");
    }

}
