package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Models.MovementType;
import Uv.DeliMgmt.backend.Repositories.InventoryMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    public InventoryMovementService(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    // Create
    public void createInventoryMovement(InventoryMovement movement) {
        inventoryMovementRepository.save(movement);
    }

    // Read All
    public List<InventoryMovement> getAllMovements() {
        return inventoryMovementRepository.findAll();
    }

    // Read by ID
    public Optional<InventoryMovement> getMovementById(Long id) {
        return inventoryMovementRepository.findById(id);
    }

    // Find by Product
    public List<InventoryMovement> findByProduct(Product product) {
        return inventoryMovementRepository.findByProduct(product);
    }

    // Find by Movement Type
    public List<InventoryMovement> findByMovementType(MovementType movementType) {
        return inventoryMovementRepository.findByMovementType(movementType);
    }

    // Delete
    public void deleteMovement(Long id) {
        inventoryMovementRepository.deleteById(id);
    }
    // Check if a product has inventory movements
    public boolean existsByProductId(Product productId) {
        return inventoryMovementRepository.existsByProduct(productId);
    }
    public void UpdateMovement(InventoryMovement updateMovement) {
        Optional<InventoryMovement> existingInventoryMovementOpt = inventoryMovementRepository.findById(updateMovement.getMovementId());

        if (existingInventoryMovementOpt.isPresent()) {
            InventoryMovement existingInventoryMovement = existingInventoryMovementOpt.get();
            // Actualiza los campos del producto existente con los del producto actualizado
            existingInventoryMovement.setMovementDate(updateMovement.getMovementDate());
            existingInventoryMovement.setMovementType(updateMovement.getMovementType());
            existingInventoryMovement.setDescription(updateMovement.getDescription());
            existingInventoryMovement.setQuantity(updateMovement.getQuantity());

            inventoryMovementRepository.save(existingInventoryMovement);  // Guardar los cambios
        } else {
            throw new RuntimeException("Product not found with id: " + updateMovement.getMovementId());
        }
    }
}
