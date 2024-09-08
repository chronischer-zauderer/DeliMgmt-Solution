package Uv.DeliMgmt.backend.Services;


import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Models.Inventory;
import Uv.DeliMgmt.backend.Repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;
    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    //post
    public Inventory createInventory(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    //get
    public List<Inventory> getAllInventories(){
        return inventoryRepository.findAll();
    }
   //Delet
   public void DeleteInventory(Long id) {
       inventoryRepository.deleteById(id);
   }

    // Update an existing Inventory
    public void UpdatedInventory(Inventory updatedInventory) {
        Optional<Inventory> existingInventoryOpt = inventoryRepository.findById(updatedInventory.getProduct().getProductId());
        if(existingInventoryOpt.isPresent()){
            Inventory existingInventory = existingInventoryOpt.get();

            existingInventory.setCurrentStock(updatedInventory.getCurrentStock());
            existingInventory.setLastUpdated(LocalDateTime.now());

            inventoryRepository.save(existingInventory);
        }else {
            throw new RuntimeException("Inventory not found for product with id: " + updatedInventory.getProduct().getProductId());
        }
    }
}