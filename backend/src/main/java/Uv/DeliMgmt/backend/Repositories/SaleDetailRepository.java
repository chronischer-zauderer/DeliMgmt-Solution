package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    List<SaleDetail> findBySale_SaleId(Long saleId);
}

