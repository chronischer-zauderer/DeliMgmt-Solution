package Uv.DeliMgmt.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Permission;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByRole(String role);
    List<Permission> findByModule(String module);
    List<Permission> findByAction(String action);
    List<Permission> findByRoleAndModule(String role, String module);
}

