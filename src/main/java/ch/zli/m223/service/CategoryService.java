package ch.zli.m223.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import ch.zli.m223.model.Category;

public class CategoryService {
    @Inject
    private EntityManager entityManager;

    public List<Category> findAll() {
        var query = entityManager.createQuery("FROM Category", Category.class);
        return query.getResultList();
    }

    @Transactional
    public Category createCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Transactional
    public Category updateCategory(Long id, Category category) {
        category.setId(id);
        entityManager.merge(category);
        return category;
    }

    @Transactional
    public Category deleteCategory(Long id) {
        List<Category> categoryList = findAll();

        for (Category category : categoryList){
            if (category.getId() == id){
                entityManager.remove(category);
                return category;
            }
        }
        return null;
    }
}
