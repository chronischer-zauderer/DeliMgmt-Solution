package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Category;
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
    // Create a new category
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    // Delete a category by its ID
    public void deleteCategory(Long categoryId) {
        Category category = findById(categoryId);
        categoryRepository.delete(category);
    }

    // Update an existing category by its ID
    public void updateCategory(Long categoryId, Category categoryDetails) {
        Category existingCategory = findById(categoryId);
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());
        // Set other fields as necessary
        categoryRepository.save(existingCategory);
    }
}
