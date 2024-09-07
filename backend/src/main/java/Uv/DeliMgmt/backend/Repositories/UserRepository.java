package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

