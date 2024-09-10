package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    // Correct method names based on entity fields
    List<Session> findByUser_UserIdAndExpiresAtIsNull(Long userId);
    Optional<Session> findBySessionToken(String token);
    List<Session> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Session> findByUser_UserIdAndExpiresAtIsNotNull(Long userId);
}
