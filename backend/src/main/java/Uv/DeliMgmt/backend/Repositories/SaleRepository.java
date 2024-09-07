package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Sale> findByCustomer(Customer customer);
    List<Sale> findByStatus(String status);



}

