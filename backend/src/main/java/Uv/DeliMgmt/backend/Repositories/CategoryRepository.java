package Uv.DeliMgmt.backend.Repositories;

import Uv.DeliMgmt.backend.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

