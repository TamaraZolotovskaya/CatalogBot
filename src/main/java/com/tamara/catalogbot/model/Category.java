package com.tamara.catalogBot.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс, представляющий категорию.
 *
 * Описывает сущность "категория", которая может использоваться для
 * организации иерархии категорий. Категории могут иметь родительские категории и названия.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    /**
     * Уникальный идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор родительской категории.
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * Название категории.
     *
     * Название категории является обязательным полем и имеет ограничение по длине
     * в 25 символов.
     */
    @Column(nullable = false, length = 25)
    private String name;

    /**
     * Конструктор класса Category.
     *
     * @param parentId Идентификатор родительской категории.
     * @param name      Название категории.
     */
    public Category(Long parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }
}

