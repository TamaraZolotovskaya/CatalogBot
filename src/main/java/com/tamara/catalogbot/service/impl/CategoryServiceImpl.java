package com.tamara.catalogBot.service.impl;

import com.tamara.catalogBot.model.Category;
import com.tamara.catalogBot.repository.CategoryRepository;
import com.tamara.catalogBot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link CategoryService} для управления категориями и их иерархией.
 *
 * Этот класс предоставляет реализацию методов для добавления, просмотра и удаления категорий.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Добавляет новую корневую категорию с указанным именем.
     *
     * @param root Название новой корневой категории.
     * @return Сообщение о результате операции.
     */
    @Override
    public String addRoot(String root) {
        if (categoryRepository.existsByParentId(0L)) {
            return "Корневой элемент " + root + " уже существует";
        }
        Category category = new Category(0L, root);
        categoryRepository.save(category);
        return "Категория " + root + " успешно добавлена";
    }

    /**
     * Добавляет новую дочернюю категорию с указанным именем к существующей родительской категории.
     *
     * @param child  Название новой дочерней категории.
     * @param parent Название родительской категории, к которой следует добавить дочернюю.
     * @return Сообщение о результате операции.
     */
    @Override
    public String add(String child, String parent) {
        if (categoryRepository.findByName(child).isPresent()) {
            return "Такая категория " + child + " уже существует";
        }
        Optional<Category> optionalCategory = categoryRepository.findByName(parent);
        if (optionalCategory.isEmpty()) {
            return "Такой категории-родителя " + parent + " не существует";
        }
        Category parentCategory = optionalCategory.get();
        Category category = new Category(parentCategory.getId(), child);
        categoryRepository.save(category);
        return "Категория " + child + " успешно добавлена";
    }

    /**
     * Возвращает структурированный вид дерева категорий.
     *
     * @return Строка, представляющая дерево категорий.
     */
    @Override
    public String view() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return "Категорий пока нет";
        }
        StringBuilder treeBuilder = new StringBuilder();
        for (Category category : categories) {
            if (category.getParentId().equals(0L)) {
                buildCategoryTree(treeBuilder, categories, category, 0);
            }
        }
        return treeBuilder.toString();
    }

    /**
     * Рекурсивный метод для построения структурированного дерева категорий в виде строки.
     *
     * @param treeBuilder  StringBuilder для добавления категорий и их отступов.
     * @param categories   Список всех категорий.
     * @param currentCategory Текущая категория, для которой строится поддерево.
     * @param depth       Глубина вложенности текущей категории в дереве.
     */
    private static void buildCategoryTree(StringBuilder treeBuilder, List<Category> categories, Category currentCategory, int depth) {
        // Добавляем отступы в зависимости от глубины вложенности
        treeBuilder.append("---".repeat(Math.max(0, depth)));
        // Добавляем название текущей категории
        treeBuilder.append(currentCategory.getName()).append("\n");
        // Рекурсивно обходим дочерние категории этой категории
        for (Category childCategory : categories) {
            if (!childCategory.getParentId().equals(0L) && childCategory.getParentId().equals(currentCategory.getId())) {
                buildCategoryTree(treeBuilder, categories, childCategory, depth + 1);
            }
        }
    }

    /**
     * Удаляет указанную категорию и все её дочерние категории.
     *
     * @param category Название категории, которую следует удалить.
     * @return Сообщение о результате операции.
     */
    @Override
    public String remove(String category)
    //здесь можно было бы также использовать рекурсивный метод, по аналогии с buildCategoryTree
    {
        Optional<Category> optionalCategory = categoryRepository.findByName(category);
        if (optionalCategory.isEmpty()) {
            return "Такой категории " + category + " не существует";
        }
        Long id = optionalCategory.get().getId();
        categoryRepository.deleteCategoryAndChildsById(id);

        return "Категория " + category + " и все её потомки успешно удалены";
    }

}
