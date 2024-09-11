package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Models.MovementType;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.InventoryMovementRepository;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(InventoryMovementRepository inventoryMovementRepository, ProductRepository productRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.productRepository = productRepository;
    }

    // Crear movimiento de inventario
    public void addInventoryMovement(InventoryMovement movement) {
        // Agrega la fecha actual al movimiento antes de guardarlo
        movement.setMovementDate(LocalDateTime.now());
        inventoryMovementRepository.save(movement);
    }

    // Obtener todos los movimientos de inventario
    public List<InventoryMovement> getAllInventoryMovements() {
        return inventoryMovementRepository.findAll();
    }

    // Obtener un movimiento de inventario por ID
    public Optional<InventoryMovement> getInventoryMovementById(Long movementId) {
        return inventoryMovementRepository.findById(movementId);
    }

    // Obtener movimientos de inventario por producto
    public List<InventoryMovement> getInventoryMovementsByProduct(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            return inventoryMovementRepository.findByProduct(productOpt.get());
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
    }

    // Obtener movimientos de inventario por tipo de movimiento (entrada/salida)
    public List<InventoryMovement> getInventoryMovementsByType(MovementType movementType) {
        return inventoryMovementRepository.findByMovementType(movementType);
    }

    // Actualizar un movimiento de inventario
    public void updateInventoryMovement(Long movementId, InventoryMovement updatedMovement) {
        Optional<InventoryMovement> existingMovementOpt = inventoryMovementRepository.findById(movementId);

        if (existingMovementOpt.isPresent()) {
            InventoryMovement existingMovement = existingMovementOpt.get();
            existingMovement.setProduct(updatedMovement.getProduct());
            existingMovement.setMovementType(updatedMovement.getMovementType());
            existingMovement.setQuantity(updatedMovement.getQuantity());
            existingMovement.setDescription(updatedMovement.getDescription());
            existingMovement.setMovementDate(LocalDateTime.now()); // Actualizamos la fecha del movimiento
            inventoryMovementRepository.save(existingMovement);
        } else {
            throw new ResourceNotFoundException("Inventory movement not found with id: " + movementId);
        }
    }

    // Eliminar un movimiento de inventario
    public void deleteInventoryMovement(Long movementId) {
        if (!inventoryMovementRepository.existsById(movementId)) {
            throw new ResourceNotFoundException("Inventory movement not found with id: " + movementId);
        }
        inventoryMovementRepository.deleteById(movementId);
    }

    // Verificar si un producto tiene movimientos de inventario
    public boolean existsInventoryMovementsForProduct(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            return inventoryMovementRepository.existsByProduct(productOpt.get());
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
    }
}
