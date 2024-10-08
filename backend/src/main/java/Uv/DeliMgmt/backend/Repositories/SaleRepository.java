package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime; // Adjusted to LocalDateTime
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate); // Adjusted to LocalDateTime
    List<Sale> findByCustomer(Customer customer);
}
