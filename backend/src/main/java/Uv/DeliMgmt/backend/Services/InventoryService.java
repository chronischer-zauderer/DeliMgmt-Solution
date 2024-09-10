package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Inventory;
import Uv.DeliMgmt.backend.Repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Create or update
    public Inventory saveOrUpdateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // Get all
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // Read one
    public Optional<Inventory> getInventoryById(Long productId) {
        return inventoryRepository.findById(productId);
    }

    // Delete
    public void deleteInventory(Long productId) {
        inventoryRepository.deleteById(productId);
    }
    public void updateInventory(Inventory updatedInventory) {
        Optional<Inventory> existingInventoryOpt = inventoryRepository.findById(updatedInventory.getProduct().getProductId());

        if (existingInventoryOpt.isPresent()) {
            Inventory existingInventory = existingInventoryOpt.get();
            // Actualiza los campos del inventario existente con los del inventario actualizado
            existingInventory.setCurrentStock(updatedInventory.getCurrentStock());
            existingInventory.setLastUpdated(LocalDateTime.now());

            inventoryRepository.save(existingInventory);  // Guardar los cambios
        } else {
            throw new RuntimeException("Inventory not found for product with id: " + updatedInventory.getProduct().getProductId());
        }
    }

}
