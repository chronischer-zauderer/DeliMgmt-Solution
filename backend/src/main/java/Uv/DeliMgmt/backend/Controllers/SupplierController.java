package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Supplier;
import Uv.DeliMgmt.backend.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createSupplier(@RequestBody Supplier supplier) {
        supplierService.createSupplier(supplier);
    }

    @GetMapping(value = "Listar", headers = "Accept=application/json")
    public List<Supplier> listSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
    public Optional<Supplier> listSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PutMapping(value = "Actualizar", headers = "Accept=application/json")
    public void updateSupplier(@RequestBody Supplier supplier) {
        supplierService.updateSupplier(supplier);
    }
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
