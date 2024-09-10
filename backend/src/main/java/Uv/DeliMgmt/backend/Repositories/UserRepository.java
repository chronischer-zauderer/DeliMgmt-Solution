package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role); // Updated to use Role enum
    Optional<User> findByEmail(String email);
    List<User> findByCreatedAtAfter(LocalDateTime date); // Ensure createdAt field exists
}
