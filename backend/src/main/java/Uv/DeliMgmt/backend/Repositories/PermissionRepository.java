package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Permission; // Import your own Permission entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByRole(String role);
    List<Permission> findByModule(String module);
    List<Permission> findByAction(String action);
    List<Permission> findByRoleAndModule(String role, String module);
}
