package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(String role);
    Optional<User> findByEmail(String email);
    List<User> findByCreatedAtAfter(LocalDateTime date);
}

