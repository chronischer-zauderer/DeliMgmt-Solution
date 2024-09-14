package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Models.MovementType;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")  // Allows requests from the frontend
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Create an inventory movement
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public ResponseEntity<Void> createInventoryMovement(@RequestBody InventoryMovement inventoryMovement) {
        inventoryService.addInventoryMovement(inventoryMovement);
        return ResponseEntity.status(201).build();
    }

    // Get all inventory movements
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public ResponseEntity<List<InventoryMovement>> getAllInventoryMovements() {
        return ResponseEntity.ok(inventoryService.getAllInventoryMovements());
    }

    // Get an inventory movement by ID
    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
    public ResponseEntity<InventoryMovement> getInventoryMovementById(@PathVariable Long id) {
        Optional<InventoryMovement> movement = inventoryService.getInventoryMovementById(id);
        return movement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get inventory movements by product
    @GetMapping(value = "listarPorProducto/{productId}", headers = "Accept=application/json")
    public ResponseEntity<List<InventoryMovement>> getInventoryMovementsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryMovementsByProduct(productId));
    }

    // Get all products
    @GetMapping(value = "listarProductos", headers = "Accept=application/json")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(inventoryService.getAllProducts());
    }

    // Create a product
    @PostMapping(value = "crearProducto", headers = "Accept=application/json")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        inventoryService.createProduct(product);
        return ResponseEntity.status(201).build();
    }

    // Update a product
    @PutMapping(value = "actualizarProducto", headers = "Accept=application/json")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        inventoryService.UpdateProduct(product);
        return ResponseEntity.noContent().build();
    }

    // Get inventory movements by type
    @GetMapping(value = "listarPorTipo/{movementType}", headers = "Accept=application/json")
    public ResponseEntity<List<InventoryMovement>> getInventoryMovementsByType(@PathVariable MovementType movementType) {
        return ResponseEntity.ok(inventoryService.getInventoryMovementsByType(movementType));
    }

    // Update an inventory movement
    @PutMapping(value = "actualizar/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> updateInventoryMovement(@PathVariable Long id, @RequestBody InventoryMovement updatedMovement) {
        inventoryService.updateInventoryMovement(id, updatedMovement);
        return ResponseEntity.noContent().build();
    }

    // Delete an inventory movement
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deleteInventoryMovement(@PathVariable Long id) {
        inventoryService.deleteInventoryMovement(id);
        return ResponseEntity.noContent().build();
    }

    // Delete a product
    @DeleteMapping(value = "eliminarProducto/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
        inventoryService.deleteInventoryMovement(id);
        return ResponseEntity.noContent().build();
    }
}
