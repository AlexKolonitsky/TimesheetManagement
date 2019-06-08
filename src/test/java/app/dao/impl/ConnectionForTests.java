package app.dao.impl;

import app.config.ApplicationInitializer;
import app.config.beans.DaoConfig;
import app.config.beans.HibernateTestConfig;
import app.config.beans.PropertyConfig;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationInitializer.class, PropertyConfig.class,
        HibernateTestConfig.class, DaoConfig.class})
@TestPropertySource(locations = {"classpath:project.tests.properties"})
public class ConnectionForTests {

    protected static IDatabaseConnection connection;

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${db.url}2")
    private String url;

    public String pathToInitialFile;

    public ConnectionForTests(String pathToInitialFile) {
        this.pathToInitialFile = pathToInitialFile;
    }

    public IDatabaseConnection getSQLConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(driver);
        Connection jdbcConnection = DriverManager.getConnection(url);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        return connection;
    }

    @Before
    public void setUp() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader()
                .getResourceAsStream(pathToInitialFile));
        connection = getSQLConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
}
