package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    public Boolean isValid(Entry entry) {
        return entry.getCheckIn().isBefore(entry.getCheckOut());
    }

    @Transactional
    public Entry createEntry(Entry entry) {
        if (isValid(entry)) {
            entityManager.persist(entry);
            return entry;
        }
        // Handle validation error, for example, throw an exception or return null.
        throw new IllegalArgumentException("CheckOut must be after CheckIn");
    }

    @Transactional
    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }

    @Transactional
    public Entry updateEntry(Long id, Entry entry) {
        if (isValid(entry)){
            entry.setId(id);
            return entityManager.merge(entry);
        }
        throw new IllegalArgumentException("CheckOut must be after CheckIn");
    }

    @Transactional
    public Entry deleteEntry(Long id) {
        List<Entry> entriesList = findAll();

        for (Entry entry : entriesList) {
            if (entry.getId() == id) {
                entityManager.remove(entry);
                return entry;
            }
        }
        return null;
    }
}
