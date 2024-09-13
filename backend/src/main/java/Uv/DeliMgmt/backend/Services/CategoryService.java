package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.CategoryRepository;
import Uv.DeliMgmt.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
    public void CreateCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> GetAllCategory() {
        return categoryRepository.findAll();
    }
    public Optional<Category> GetCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
    public void deleteCategory(Long categoryId) {
        // Busca la categoría por su ID
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            // Verifica si la categoría tiene productos asociados
            List<Product> products = productRepository.findByCategory(category);
            if (!products.isEmpty()) {
                throw new IllegalStateException("La categoría tiene productos asociados y no se puede eliminar.");
            }

            // Si no hay productos asociados, elimina la categoría
            categoryRepository.deleteById(categoryId);
        } else {
            throw new RuntimeException("Categoría no encontrada con ID: " + categoryId);
        }
    }
    public void UpdateCategory(Category updatedcategory) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(updatedcategory.getCategoryId());

        if (existingCategoryOpt.isPresent()) {
            Category existingProduct = existingCategoryOpt.get();
            // Actualiza los campos del producto existente con los del producto actualizado
            existingProduct.setName(updatedcategory.getName());
            existingProduct.setDescription(updatedcategory.getDescription());

            categoryRepository.save(existingProduct);  // Guardar los cambios
        } else {
            throw new RuntimeException("Product not found with id: " + updatedcategory.getCategoryId());
        }
    }
    public void DeleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
