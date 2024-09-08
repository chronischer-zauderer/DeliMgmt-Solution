package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Search a category by its ID
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    //create
    public void CreateCategory(Category category) {
        categoryRepository.save(category);
    }

    // Delete a category by its ID
    public void deleteCategory(Long categoryId) {
        Category category = findById(categoryId);
        categoryRepository.delete(category);
    }

    // Update an existing category
    public void updateCategory(Long categoryId, Category categoryDetails) {
        Category existingCategory = findById(categoryId);

        existingCategory.setName(categoryDetails.getName());
    }

    public Category Updatecategory(Long id, Product updatecategory) {
        return categoryRepository.findById(id).map(product -> {
            product.setName(categoryRepository.getName());
            product.setDescription(categoryRepository.getDescription());
            product.setName(categoryRepository.getPrice());
            product.setCategoryId(categoryRepository.getCategory());
            product.setDescription(categoryRepository.getStockQuantity());

            // Update other fields as needed
            return categoryRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

}
