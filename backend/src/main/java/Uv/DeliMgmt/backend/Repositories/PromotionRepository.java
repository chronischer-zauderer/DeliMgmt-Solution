package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByNameContainingIgnoreCase(String name);
    List<Promotion> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
}
