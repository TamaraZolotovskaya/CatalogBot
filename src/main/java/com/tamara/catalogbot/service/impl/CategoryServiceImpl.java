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
 * <p>
 * Этот класс предоставляет реализацию методов для добавления, просмотра и удаления категорий.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Добавляет новую корневую категорию с указанным именем.
     *
     * @param root   Название новой корневой категории.
     * @param chatId Идентификатор чата.
     * @return Сообщение о результате операции.
     */
    @Override
    public String addRoot(String root, long chatId) {
        if (!categoryRepository.findByChatIdAndParentId(chatId, 0L).isEmpty()) {
            return "Корневой элемент " + root + " уже существует";
        }
        Category category = new Category(0L, root, chatId);
        categoryRepository.save(category);
        return "Категория " + root + " успешно добавлена";
    }

    /**
     * Добавляет новую дочернюю категорию с указанным именем к существующей родительской категории.
     *
     * @param child  Название новой дочерней категории.
     * @param parent Название родительской категории, к которой следует добавить дочернюю.
     * @param chatId Идентификатор чата.
     * @return Сообщение о результате операции.
     */
    @Override
    public String add(String child, String parent, long chatId) {
        if (!categoryRepository.findByChatIdAndName(chatId, child).isEmpty()) {
            return "Такая категория " + child + " уже существует";
        }
        Optional<Category> optionalCategory = categoryRepository.findByChatIdAndName(chatId, parent);
        if (optionalCategory.isEmpty()) {
            return "Такой категории-родителя " + parent + " не существует";
        }
        Category parentCategory = optionalCategory.get();
        Category category = new Category(parentCategory.getId(), child, chatId);
        categoryRepository.save(category);
        return "Категория " + child + " успешно добавлена";
    }

    /**
     * Возвращает структурированный вид дерева категорий.
     *
     * @param chatId Идентификатор чата.
     * @return Строка, представляющая дерево категорий.
     */
    @Override
    public String view(long chatId) {
        List<Category> categories = categoryRepository.findByChatId(chatId);
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
     * @param treeBuilder     StringBuilder для добавления категорий и их отступов.
     * @param categories      Список всех категорий.
     * @param currentCategory Текущая категория, для которой строится поддерево.
     * @param depth           Глубина вложенности текущей категории в дереве.
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
     * @param chatId   Идентификатор чата.
     * @return Сообщение о результате операции.
     */
    @Override
    public String remove(String category, long chatId)
    //здесь можно было бы также использовать рекурсивный метод, по аналогии с buildCategoryTree
    {
        Optional<Category> optionalCategory = categoryRepository.findByChatIdAndName(chatId, category);
        if (optionalCategory.isEmpty()) {
            return "Такой категории " + category + " не существует";
        }
        Long id = optionalCategory.get().getId();
        categoryRepository.deleteCategoryAndChildsByChatIdAndId(chatId, id);

        return "Категория " + category + " и все её потомки успешно удалены";
    }

}
