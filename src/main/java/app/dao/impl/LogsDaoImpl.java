package app.dao.impl;

import app.dao.BasicCrudDao;
import app.dao.LogsDao;
import app.entities.Assignment;
import app.entities.Logs;
import app.entities.namespace.LogsNamespace;
import app.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import java.util.*;

@Repository
@Transactional
public class LogsDaoImpl implements BasicCrudDao<Logs>, LogsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Logs findById(int id) {
        Logs logs = sessionFactory.getCurrentSession()
                .get(Logs.class, id);
        if (logs == null) {
            throw new EntityNotFoundException();
        }
        return logs;
    }

    @Override
    public List<Logs> findAll() {
        Query query
                = sessionFactory.getCurrentSession()
                .createQuery("from Logs");
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        sessionFactory.getCurrentSession()
                .delete(findById(id));
    }

    @Override
    public void create(Logs entity) {
        sessionFactory.getCurrentSession()
                .save(entity);
    }

    @Override
    public void delete(Logs entity) {
        sessionFactory.getCurrentSession()
                .delete(entity);
    }

    @Override
    public void update(Logs entity) {
        sessionFactory.getCurrentSession()
                .update(entity);
    }

    @Override
    public List<Logs> getLogsByAssignmentId(List<Assignment> assignmentList) {
        List<Integer> logsIds = new ArrayList<>();
        for(Assignment assignment : assignmentList) {
            logsIds.add(assignment.getId());
        }
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Logs where assignmentId in " + logsIds.toArray());
        return query.getResultList();
    }

}
