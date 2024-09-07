package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
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
    Optional<Product> GetProductById(Long id) {
        return productRepository.findById(id);
    }
    //Delete
    public void DeleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
