package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.PermissionEntity; // Import your own Permission entity
import Uv.DeliMgmt.backend.Models.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

}
