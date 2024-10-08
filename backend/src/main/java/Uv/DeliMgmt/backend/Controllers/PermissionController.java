package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.PermissionEntity;
import Uv.DeliMgmt.backend.Models.PermissionEntity;
import Uv.DeliMgmt.backend.Services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<Void> createPermission(@RequestBody PermissionEntity permission) {
        permissionService.createPermission(permission);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public ResponseEntity<List<PermissionEntity>> listPermissions() {
        List<PermissionEntity> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<PermissionEntity> getPermissionById(@PathVariable long id) {
        Optional<PermissionEntity> permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<Void> updatePermission(@RequestBody PermissionEntity permission) {
        permissionService.updatePermission(permission);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deletePermission(@PathVariable long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}