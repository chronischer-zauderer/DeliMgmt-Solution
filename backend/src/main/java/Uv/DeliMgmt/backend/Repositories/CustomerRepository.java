package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}

