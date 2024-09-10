package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByNameContaining(String name);

}
