package Uv.DeliMgmt.backend.Controllers;


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

    // Delete a product
    @DeleteMapping(value = "eliminarProducto/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
