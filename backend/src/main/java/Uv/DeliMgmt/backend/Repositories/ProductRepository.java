package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByName(String name);
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByStockQuantityLessThan(Integer stockQuantity);
    List<Product> findByStockQuantityGreaterThan(Integer stockQuantity);
    List<Product> findByStockQuantityLessThanEqual(Integer stockQuantity);

    // Find by category ID if needed
    List<Product> findByCategory_CategoryId(Long categoryId);

}
