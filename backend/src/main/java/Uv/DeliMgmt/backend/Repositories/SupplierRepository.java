package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByNameContainingIgnoreCase(String name);
    //List<Supplier> findByLocation(String location);
}
