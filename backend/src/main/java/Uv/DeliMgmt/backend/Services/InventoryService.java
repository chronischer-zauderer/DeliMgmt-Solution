package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Inventory;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.InventoryRepository;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService( ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    // Obtener movimientos de inventario por producto
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void createProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        // Check if there are any inventory movements for the product
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + productId));

        inventoryRepository.deleteById(productId);
        // Finally, delete the product
        productRepository.deleteById(productId);
    }




    // Verificar si un producto tiene movimientos de inventario
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

    public Optional<Product> GetProductById(long id) {
        return productRepository.findById(id);
    }
}
