package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Create
    public void CreateProduct(Product product) {
        productRepository.save(product);
    }

    //get all
    public List<Product> GetAllProducts() {
        return productRepository.findAll();
    }

    //Read one
    public Optional<Product> GetProductById(Long id) {
        return productRepository.findById(id);
    }

    //Delete
    public void DeleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    public Product UpdateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            // Actualizar otros campos según sea necesario
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
}
