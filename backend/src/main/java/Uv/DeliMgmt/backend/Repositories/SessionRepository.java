package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserIdAndEndTimeIsNull(Long userId);
    Optional<Session> findByToken(String token);
    List<Session> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Session> findByUserIdAndEndTimeIsNotNull(Long userId);
}

