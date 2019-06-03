package app.dao;

import app.entities.Integration;

public interface IntegrationDao extends BasicCrudDao<Integration> {

    Object findByLogin(String login);
}
