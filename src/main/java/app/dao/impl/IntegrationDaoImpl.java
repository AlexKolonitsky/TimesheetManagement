package app.dao.impl;

import app.dao.IntegrationDao;
import app.entities.Integration;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class IntegrationDaoImpl extends BasicCrudDaoImpl<Integration> implements IntegrationDao {

    private static final String FIND_BY_LOGIN = "select * from integrations where login = ?";

    @Override
    public Object findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(FIND_BY_LOGIN);
        query.setParameter(1, login);
        return query.getSingleResult();
    }
}
