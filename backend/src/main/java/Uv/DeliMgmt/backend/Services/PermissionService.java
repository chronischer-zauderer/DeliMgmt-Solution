package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Permission;
import Uv.DeliMgmt.backend.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // Create
    public void createPermission(Permission permission) {
        permissionRepository.save(permission);
    }

    // Get all
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Get one by ID
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    // Update
    public void updatePermission(Permission updatedPermission) {
        Optional<Permission> existingPermissionOpt = permissionRepository.findById(updatedPermission.getPermissionId());

        if (existingPermissionOpt.isPresent()) {
            Permission existingPermission = existingPermissionOpt.get();
            existingPermission.setRole(updatedPermission.getRole());
            existingPermission.setAction(updatedPermission.getAction());
            existingPermission.setModule(updatedPermission.getModule());
            permissionRepository.save(existingPermission);
        } else {
            throw new RuntimeException("Permission not found with id: " + updatedPermission.getPermissionId());
        }
    }

    // Delete
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
