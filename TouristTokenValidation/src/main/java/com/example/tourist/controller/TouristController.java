package com.example.tourist.controller;

import com.example.tourist.model.Tourist;
import com.example.tourist.repository.TouristRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristRepository repository;

    public TouristController(TouristRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tourist> getAllTourists() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTourist(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tourist> addTourist(
            @RequestBody Tourist tourist) {

        tourist.setId(null);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repository.save(tourist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTourist(
            @PathVariable Long id,
            @Parameter(description = "Token for validating deletion")
            @RequestHeader(value = "Token", required = false)
            String token) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Tourist not found"));
        }

        repository.deleteById(id);

        return ResponseEntity.ok(
                Map.of("message",
                        "Tourist record deleted successfully"));
    }
}
