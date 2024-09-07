package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {}

