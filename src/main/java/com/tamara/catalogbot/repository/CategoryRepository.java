package com.tamara.catalogBot.repository;

import com.tamara.catalogBot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Репозиторий для работы с категориями.
 *
 * Интерфейс `CategoryRepository` предоставляет методы для работы с категориями в базе данных,
 * расширяет интерфейс `JpaRepository`.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Проверяет наличие категории с указанным родительским идентификатором.
     *
     * @param parentId Идентификатор родительской категории.
     * @return `true`, если категория с указанным родительским идентификатором существует, в противном случае `false`.
     */
    boolean existsByParentId(Long parentId);

    /**
     * Находит категорию по её имени.
     *
     * @param name Название категории.
     * @return Опциональный объект, содержащий категорию с указанным именем, если она существует.
     */
    Optional<Category> findByName(String name);

    /**
     * Удаляет категорию и все её дочерние категории рекурсивно по идентификатору.
     *
     * @param id Идентификатор категории, которую следует удалить вместе с дочерними категориями.
     */
    @Transactional
    @Modifying
    @Query(value = "WITH RECURSIVE category_tree(id) AS (SELECT id FROM category WHERE id = ?1 " +
            "UNION ALL SELECT c.id FROM category c JOIN category_tree ct ON c.parent_id = ct.id) " +
            "DELETE FROM category WHERE id IN (SELECT id FROM category_tree)",
            nativeQuery = true)
    void deleteCategoryAndChildsById(Long id);

}