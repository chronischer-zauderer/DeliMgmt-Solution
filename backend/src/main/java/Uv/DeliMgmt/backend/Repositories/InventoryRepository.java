package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {


    // Encuentra un inventario por el ID del producto
    Optional<Inventory> findByProductId(Long productId);

    // Encuentra todos los inventarios con stock por encima del valor dado
    List<Inventory> findByCurrentStockGreaterThan(Integer stock);

    // Encuentra todos los inventarios con stock por debajo del valor dado
    List<Inventory> findByCurrentStockLessThan(Integer stock);

    // Encuentra todos los inventarios que fueron actualizados después de una fecha dada
    List<Inventory> findByLastUpdatedAfter(LocalDateTime dateTime);

    // Encuentra todos los inventarios que fueron actualizados antes de una fecha dada
    List<Inventory> findByLastUpdatedBefore(LocalDateTime dateTime);

}
