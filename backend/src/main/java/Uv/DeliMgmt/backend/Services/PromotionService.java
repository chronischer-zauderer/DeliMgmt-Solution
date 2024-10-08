package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Promotion;
import Uv.DeliMgmt.backend.Repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    // Create
    public void createPromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    // Get all
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    // Get one by ID
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }

    // Update
    public void updatePromotion(Promotion updatedPromotion) {
        Optional<Promotion> existingPromotionOpt = promotionRepository.findById(updatedPromotion.getPromotionId());

        if (existingPromotionOpt.isPresent()) {
            Promotion existingPromotion = existingPromotionOpt.get();
            existingPromotion.setDiscount(updatedPromotion.getDiscount());
            existingPromotion.setStartDate(updatedPromotion.getStartDate());
            existingPromotion.setEndDate(updatedPromotion.getEndDate());
            promotionRepository.save(existingPromotion);
        } else {
            throw new RuntimeException("Promotion not found with id: " + updatedPromotion.getPromotionId());
        }
    }

    // Delete
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }
}
