package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createCategory(@RequestBody Category category) {
        categoryService.CreateCategory(category);
    }
    @GetMapping(value = "Listar", headers = "Accept=application/json")
    public List<Category> listCategory() {
        return categoryService.GetAllCategory();
    }
    @GetMapping(value = "listarPorId/{id}",headers = "Accept=application/json")
    public Optional<Category> listCategoryPorId(@PathVariable Long id) {
        return categoryService.GetCategoryById(id);
    }
    @PutMapping(value = "Actualizar", headers = "Accept=application/json")
    public void updateCategory(@RequestBody Category category) {
        categoryService.UpdateCategory(category);
    }
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.DeleteCategory(id); // Llamar al servicio para eliminar la categoría
    }

}