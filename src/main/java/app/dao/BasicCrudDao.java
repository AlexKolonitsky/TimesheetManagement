package app.dao;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BasicCrudDao<T> {

    T findById(int id);

    T findByCode(String code);

    List<T> findAll();

    void deleteById(int id);

    void create(T entity);

    void delete(T entity);

    void update(T entity);
}
