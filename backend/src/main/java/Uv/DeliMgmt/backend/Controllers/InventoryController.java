package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Models.MovementType;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")  // Permite solicitudes desde el frontend
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Crear un movimiento de inventario
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createInventoryMovement(@RequestBody InventoryMovement inventoryMovement) {
        inventoryService.addInventoryMovement(inventoryMovement);
    }

    // Obtener todos los movimientos de inventario
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<InventoryMovement> getAllInventoryMovements() {
        return inventoryService.getAllInventoryMovements();
    }

    // Obtener un movimiento de inventario por ID
    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
    public Optional<InventoryMovement> getInventoryMovementById(@PathVariable Long id) {
        return inventoryService.getInventoryMovementById(id);
    }

    // Obtener movimientos de inventario por producto
    @GetMapping(value = "listarPorProducto/{productId}", headers = "Accept=application/json")
    public List<InventoryMovement> getInventoryMovementsByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryMovementsByProduct(productId);
    }
    @GetMapping(value = "listarProductos", headers = "Accept=application/json")
    public List<Product> getAllProducs() {
        return inventoryService.getAllProducts();
    }
    @PostMapping(value = "CrearProducto",headers = "Accept=application/json")
    public void CreateProduct(@RequestBody Product product) {
        inventoryService.createProduct(product);
    }

    // Obtener movimientos de inventario por tipo (entrada/salida)
    @GetMapping(value = "listarPorTipo/{movementType}", headers = "Accept=application/json")
    public List<InventoryMovement> getInventoryMovementsByType(@PathVariable MovementType movementType) {
        return inventoryService.getInventoryMovementsByType(movementType);
    }

    // Actualizar un movimiento de inventario
    @PutMapping(value = "actualizar/{id}", headers = "Accept=application/json")
    public void updateInventoryMovement(@PathVariable Long id, @RequestBody InventoryMovement updatedMovement) {
        inventoryService.updateInventoryMovement(id, updatedMovement);
    }

    // Eliminar un movimiento de inventario
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteInventoryMovement(@PathVariable Long id) {
        inventoryService.deleteInventoryMovement(id);
    }
    @DeleteMapping(value = "eliminarProducto/{id}", headers = "Accept=application/json")
    public void deleteProduct(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
    }
}
