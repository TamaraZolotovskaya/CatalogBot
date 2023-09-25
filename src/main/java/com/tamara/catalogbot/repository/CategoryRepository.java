package com.tamara.catalogBot.repository;

import com.tamara.catalogBot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByParentId(Long parentId);

    Optional<Category> findByName(String name);

    @Transactional
    @Modifying
    @Query
            (value = "WITH RECURSIVE category_tree(id) AS (SELECT id FROM category WHERE id = ?1 " +
                    "UNION ALL SELECT c.id FROM category c JOIN category_tree ct ON c.parent_id = ct.id) " +
                    "DELETE FROM category WHERE id IN (SELECT id FROM category_tree)",
                    nativeQuery = true)
    void deleteCategoryAndChildsById(Long id);
}
