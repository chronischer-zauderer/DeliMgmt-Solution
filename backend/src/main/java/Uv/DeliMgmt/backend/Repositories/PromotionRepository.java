package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByStartDateBeforeAndEndDateAfter(LocalDate date);
    List<Promotion> findByDiscountGreaterThanEqual(BigDecimal discount);
}


