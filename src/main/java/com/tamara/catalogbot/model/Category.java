package com.tamara.catalogBot.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId; //id категории-родителя

    @Column(nullable = false, length = 25)
    private String name; //название категории

    public Category(Long parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

}
