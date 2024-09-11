package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Seller;
import Uv.DeliMgmt.backend.Repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    // Create
    public void createSeller(Seller seller) {
        sellerRepository.save(seller);
    }

    // Get all
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    // Get one by ID
    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    // Update
    public void updateSeller(Seller updatedSeller) {
        Optional<Seller> existingSellerOpt = sellerRepository.findById(updatedSeller.getSellerId());

        if (existingSellerOpt.isPresent()) {
            Seller existingSeller = existingSellerOpt.get();
            existingSeller.setName(updatedSeller.getName());
            existingSeller.setPhone(updatedSeller.getPhone());
            existingSeller.setEmail(updatedSeller.getEmail());
            sellerRepository.save(existingSeller);
        } else {
            throw new RuntimeException("Seller not found with id: " + updatedSeller.getSellerId());
        }
    }
    // Delete
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }
}
