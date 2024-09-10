package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createProduct(@RequestBody Product product) {
        productService.CreateProduct(product);
    }
    @GetMapping(value = "Listar", headers = "Accept=application/json")
    public List<Product> listarProducts() {
        return productService.GetAllProducts();
    }
    @GetMapping(value = "listarPorId/{id}",headers = "Accept=application/json")
    public Optional<Product> listarPorId(@PathVariable long id) {
        return productService.GetProductById(id);
    }
    //update
    @PutMapping(value = "Actualizar", headers = "Accept=application/json")
    public void updateProduct(@RequestBody Product product) {
        productService.UpdateProduct(product);
    }
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}
