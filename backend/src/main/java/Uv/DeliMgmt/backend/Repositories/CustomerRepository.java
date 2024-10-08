package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findByCustomerId(Long customerId);
}



