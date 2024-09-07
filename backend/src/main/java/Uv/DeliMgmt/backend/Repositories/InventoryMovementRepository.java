package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.InventoryMovement;
import Uv.DeliMgmt.backend.Models.MovementType;
import Uv.DeliMgmt.backend.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findByProduct(Product product);
    List<InventoryMovement> findByMovementType(MovementType movementType);
}

