package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {}

