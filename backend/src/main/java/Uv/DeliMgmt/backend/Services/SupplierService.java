package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Supplier;
import Uv.DeliMgmt.backend.Repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // Create
    public void createSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }
    // Read All
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    // Read by ID
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }
    // Update
    public void updateSupplier(Supplier updatedSupplier) {
        Optional<Supplier> existingSupplierOpt = supplierRepository.findById(updatedSupplier.getSupplierId());

        if (existingSupplierOpt.isPresent()) {
            Supplier existingSupplier = existingSupplierOpt.get();
            existingSupplier.setName(updatedSupplier.getName());
            existingSupplier.setContactPerson(updatedSupplier.getContactPerson());
            supplierRepository.save(existingSupplier);
        } else {
            throw new RuntimeException("Supplier not found with id: " + updatedSupplier.getSupplierId());
        }
    }
    // Delete
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
