package io.hosuaby.webflow.repositories;

import io.hosuaby.webflow.domain.Teapot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Map based repository for teapots. No exceptions are thrown at the repository
 * level. This repository is thread safe.
 */
@Repository
public class TeapotRepository implements CrudRepository<Teapot, String> {

    /**
     * Map store for the teapots.
     */
    private Map<String, Teapot> teapotStore;

    /**
     * Default constructor.
     */
    public TeapotRepository() {
        teapotStore = new HashMap<>();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized long count() {
        return teapotStore.size();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(String id) {
        teapotStore.remove(id);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(Teapot teapot) {
        teapotStore.remove(teapot.getId());
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void delete(Iterable<? extends Teapot> teapots) {
        for (Teapot teapot : teapots) {
            teapotStore.remove(teapot.getId());
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void deleteAll() {
        teapotStore.clear();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean exists(String id) {
        return teapotStore.containsKey(id);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Iterable<Teapot> findAll() {
        return teapotStore.values();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Iterable<Teapot> findAll(Iterable<String> ids) {
        Set<Teapot> teapots = new HashSet<>();
        for (String id : ids) {
            Teapot teapot = teapotStore.get(id);
            if (teapot != null) {
                teapots.add(teapot);
            }
        }
        return teapots;
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Teapot findOne(String id) {
        return teapotStore.get(id);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized <S extends Teapot> S save(S teapot) {
        return (S) teapotStore.put(teapot.getId(), teapot);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized <S extends Teapot> Iterable<S> save(Iterable<S> teapots) {
        Set<S> savedTeapots = new HashSet<>();
        for (S teapot : teapots) {
            savedTeapots.add((S) teapotStore.put(teapot.getId(), teapot));
        }
        return savedTeapots;
    }

}
