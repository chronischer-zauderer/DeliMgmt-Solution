package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.PermissionEntity;
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
    public void createPermission(PermissionEntity permission) {
        permissionRepository.save(permission);
    }

    // Get all
    public List<PermissionEntity> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Get one by ID
    public Optional<PermissionEntity> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    // Update
    public void updatePermission(PermissionEntity updatedPermission) {
        Optional<PermissionEntity> existingPermissionOpt = permissionRepository.findById(updatedPermission.getId());

        if (existingPermissionOpt.isPresent()) {
            PermissionEntity existingPermission = existingPermissionOpt.get();
            existingPermission.setId(updatedPermission.getId());
            existingPermission.setName(updatedPermission.getName());
            permissionRepository.save(existingPermission);
        } else {
            throw new RuntimeException("Permission not found with id: " + updatedPermission.getId());
        }
    }

    // Delete
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
