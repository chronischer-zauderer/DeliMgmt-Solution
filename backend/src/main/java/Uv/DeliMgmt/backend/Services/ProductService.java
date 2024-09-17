package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void deleteProduct(Long productId) {
        // Busca el producto por su ID
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();


            // Si no hay movimientos, elimina el producto
            productRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }
    // ProductService.java

    public void UpdateProduct(Product updatedProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(updatedProduct.getProductId());

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            // Actualiza los campos del producto existente con los del producto actualizado
            existingProduct.setProductCode(updatedProduct.getProductCode());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setSupplier(updatedProduct.getSupplier());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            existingProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.save(existingProduct);  // Guardar los cambios
        } else {
            throw new RuntimeException("Product not found with id: " + updatedProduct.getProductId());
        }
    }

}
