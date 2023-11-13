package ch.zli.m223.service;

import java.sql.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.h2.command.dml.Update;
import org.jboss.resteasy.reactive.RestPath;

import ch.zli.m223.model.Entry;
import io.vertx.codegen.doc.Text;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }

    @Transactional
    public Entry updateEntry(Long id, Entry entry) {
        entry.setId(id);
        return entityManager.merge(entry);
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
