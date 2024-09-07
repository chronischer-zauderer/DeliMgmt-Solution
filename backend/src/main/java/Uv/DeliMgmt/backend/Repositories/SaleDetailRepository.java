package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Promotion;
import Uv.DeliMgmt.backend.Models.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    List<SaleDetail> findBySaleId(Long saleId);
}
