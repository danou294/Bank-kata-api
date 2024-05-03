package com.example.bankkata.domain.adapter;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livrets-epargne")
public class LivretEpargneController {

    private final LivretEpargneService livretEpargneService;

    public LivretEpargneController(LivretEpargneService livretEpargneService) {
        this.livretEpargneService = livretEpargneService;
    }

    @PostMapping("/create")
    public ResponseEntity<LivretEpargne> createLivretEpargne(@RequestParam double plafondDepot) {
        LivretEpargne livretEpargne = livretEpargneService.createLivretEpargne(plafondDepot);
        return ResponseEntity.status(HttpStatus.CREATED).body(livretEpargne);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivretEpargne> getLivretEpargneById(@PathVariable Long id) {
        LivretEpargne livretEpargne = livretEpargneService.getLivretEpargneById(id);
        if (livretEpargne != null) {
            return ResponseEntity.ok(livretEpargne);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivretEpargne> updatePlafondDepot(@PathVariable Long id, @RequestParam double nouveauPlafond) {
        LivretEpargne livretEpargne = livretEpargneService.updatePlafondDepot(id, nouveauPlafond);
        if (livretEpargne != null) {
            return ResponseEntity.ok(livretEpargne);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivretEpargne(@PathVariable Long id) {
        livretEpargneService.deleteLivretEpargne(id);
        return ResponseEntity.noContent().build();
    }
}
