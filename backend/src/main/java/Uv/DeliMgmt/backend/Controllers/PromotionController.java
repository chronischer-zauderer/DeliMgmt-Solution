package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Promotion;
import Uv.DeliMgmt.backend.Services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<Void> createPromotion(@RequestBody Promotion promotion) {
        promotionService.createPromotion(promotion);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public ResponseEntity<List<Promotion>> listPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable long id) {
        Optional<Promotion> promotion = promotionService.getPromotionById(id);
        return promotion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<Void> updatePromotion(@RequestBody Promotion promotion) {
        promotionService.updatePromotion(promotion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deletePromotion(@PathVariable long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}