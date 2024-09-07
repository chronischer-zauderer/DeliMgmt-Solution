package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByName(String name);

    List<Product> findByPrice(String description);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceGreaterThan(Double price);

    List<Product> findByStockLessThan(Integer stock);

    List<Product> findByStockGreaterThan(Integer stock);

    List<Product> findByStockLessThanEqual(Integer stock);

}

