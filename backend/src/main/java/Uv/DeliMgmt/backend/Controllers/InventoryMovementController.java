package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Services.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryMovementController {
    private final InventoryMovementService inventoryMovementService;

    @Autowired
    public InventoryMovementController(InventoryMovementService inventoryMovementService) {
        this.inventoryMovementService = inventoryMovementService;
    }

    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createMovement(@RequestBody InventoryMovement movement) {
        inventoryMovementService.createInventoryMovement(movement);
    }

    @GetMapping(value = "Listar", headers = "Accept=application/json")
    public List<InventoryMovement> listMovements() {
        return inventoryMovementService.getAllMovements();
    }

    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
    public Optional<InventoryMovement> listMovementById(@PathVariable Long id) {
        return inventoryMovementService.getMovementById(id);
    }

    @PutMapping(value = "Actualizar", headers = "Accept=application/json")
    public void updateMovement(@RequestBody InventoryMovement movement) {
        inventoryMovementService.UpdateMovement(movement);
    }

    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteMovement(@PathVariable Long id) {
        inventoryMovementService.deleteMovement(id);
    }
}
