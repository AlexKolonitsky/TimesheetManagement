package app.dao.impl;

import app.dao.BasicCrudDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class BasicCrudDaoImpl<T> implements BasicCrudDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<? extends T> daoType;

    public BasicCrudDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        daoType = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public T findById(int id) {
        T entity = sessionFactory.getCurrentSession().get(daoType, id);
        if (entity == null) {
            throw new NoSuchElementException();
        }
        return entity;
    }

    @Override
    public List<T> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + daoType.getName()).getResultList();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        T entity = session.load(daoType, id);
        session.delete(entity);
    }

    @Override
    public void create(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public T findByCode(String code) {
        T entity = sessionFactory.getCurrentSession().get(daoType, code);
        if (entity == null) {
            throw new NoSuchElementException();
        }
        return entity;
    }
}
