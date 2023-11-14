package ch.zli.m223.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Tag;

public class TagService {
    @Inject
    private EntityManager entityManager;

    public List<Tag> findAll() {
        var query = entityManager.createQuery("FROM Tag", Tag.class);
        return query.getResultList();
    }

    @Transactional
    public Tag createTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        tag.setId(id);
        entityManager.merge(tag);
        return tag;
    }

    @Transactional
    public Tag deleteTag(Long id) {
        List<Tag> tagList = findAll();

        for (Tag tag : tagList) {
            if (tag.getId() == id) {
                entityManager.remove(tag);
                return tag;
            }
        }
        return null;
    }
}
