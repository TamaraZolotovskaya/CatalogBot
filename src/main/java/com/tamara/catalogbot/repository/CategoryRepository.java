package com.tamara.catalogBot.repository;

import com.tamara.catalogBot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс содержит методы для работы с категориями в базе данных,
 * расширяет интерфейс `JpaRepository`.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Находит список категорий по идентификатору чата.
     *
     * @param chatId идентификатор чата, для которого необходимо найти категории.
     * @return список категорий, принадлежащих указанному чату.
     */
    List<Category> findByChatId(long chatId);


    /**
     * Находит список категорий по идентификатору чата и идентификатору родительской категории.
     *
     * @param chatId   идентификатор чата, для которого необходимо найти категории.
     * @param parentId идентификатор родительской категории, для которой необходимо найти дочерние категории.
     * @return список категорий, принадлежащих указанному чату и имеющих указанный родительский id.
     */
    List<Category> findByChatIdAndParentId(long chatId, Long parentId);


    /**
     * Находит категорию по идентификатору чата и имени.
     *
     * @param chatId Идентификатор чата.
     * @param name   Название категории.
     * @return Опциональный объект, содержащий категорию с указанным именем, если она существует для данного чата.
     */
    Optional<Category> findByChatIdAndName(long chatId, String name);


    /**
     * Удаляет категорию и все её дочерние категории рекурсивно по идентификатору для данного чата.
     *
     * @param chatId Идентификатор чата.
     * @param id     Идентификатор категории, которую следует удалить вместе с дочерними категориями.
     */
    @Transactional
    @Modifying
    @Query(value = "WITH RECURSIVE category_tree(chat_id, id) AS (SELECT chat_id, id FROM category WHERE chat_id = ?1 AND id = ?2 " +
            "UNION ALL SELECT c.id, c.chat_id FROM category c JOIN category_tree ct ON c.parent_id = ct.id) " +
            "DELETE FROM category WHERE id IN (SELECT id FROM category_tree)",
            nativeQuery = true)
    void deleteCategoryAndChildsByChatIdAndId(long chatId, Long id);
}