package app.dao.impl;

import app.config.beans.DaoConfig;
import app.config.beans.HibernateConfig;
import app.config.beans.PropertyConfig;
import app.dao.BasicCrudDao;
import app.entities.Invitation;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class, PropertyConfig.class, HibernateConfig.class})
public class InvitationsDaoImplTest extends DBTestCase {

    @Autowired
    protected BasicCrudDao<Invitation> basicCrudDao;

    private static final int NUMBER_OF_FIRST_ROW = 0;

    private String driver;
    private String url;
    private String username;
    private String password;
    private final String schema = "timesheet_dev";
    protected final String table = "Invitations";

    public InvitationsDaoImplTest() {
        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(getClass()
                    .getClassLoader()
                    .getResourceAsStream("project.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("jdbc.driver");
        url = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, driver);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, url);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, username);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, password);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, schema);
    }

    @Before
    public void setUp() throws Exception {
        IDataSet data = getDataSet();
        IDatabaseConnection connection = getMySqlConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, data);
    }

    protected IDatabaseConnection getMySqlConnection() throws Exception {
        IDatabaseConnection connection = super.getConnection();
        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        return connection;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader()
                .getResourceAsStream("app/dao/impl/invitationDataSet/initilization-dataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Test
    public void setUpDatabaseTest() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader()
                .getResourceAsStream("app/dao/impl/invitationDataSet/initilization-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(table);
        IDataSet actualDataSet = getMySqlConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(table);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteById() {
        basicCrudDao.deleteById(4);
        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream("app/dao/impl/invitationDataSet/delete-dataset.xml"));
            ITable iTable = iDataSet.getTable(table);

            IDataSet actualDataSet = getMySqlConnection().createDataSet();
            ITable actualITable = actualDataSet.getTable(table);
            Assertion.assertEquals(iTable, actualITable);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByObject() {
        basicCrudDao.delete(new Invitation(
                1, 1,
                "invatationCode", new Date(2019, 03, 03), "status"
        ));

        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream(""));
            ITable iTable = iDataSet.getTable(table);

            IDataSet actualDataSet = getMySqlConnection().createDataSet();
            ITable actualITable = actualDataSet.getTable(table);
            Assertion.assertEquals(iTable, actualITable);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void deleteInvitationException() {
        basicCrudDao.deleteById(8);
    }

    @Test
    public void getAllInvitation() {
        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().
                    getResourceAsStream("app/dao/impl/invitationDataSet/getAll-dataset.xml"));
            ITable iTable = iDataSet.getTable(table);
            List<Invitation> invitationList = basicCrudDao.findAll();
            int index = NUMBER_OF_FIRST_ROW;
            for (Invitation invitation : invitationList) {
                Assert.assertEquals(iTable.getValue(index, "id").toString(),
                        String.valueOf(invitation.getId()));
                Assert.assertEquals(iTable.getValue(index, "partnerId").toString(),
                        String.valueOf(invitation.getPartnerId()));
                Assert.assertEquals(iTable.getValue(index, "code").toString(),
                        String.valueOf(invitation.getCode()));
                Assert.assertEquals(iTable.getValue(index, "dateEnd").toString(),
                        String.valueOf(invitation.getDateEnd()));
                Assert.assertEquals(iTable.getValue(index, "status").toString(),
                        String.valueOf(invitation.getStatus()));
                index++;
            }
        } catch (DataSetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFindById() {
        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream("app/dao/impl/invitationDataSet/getFindById-dataset.xml"));
            ITable iTable = iDataSet.getTable(table);

            Invitation invitation = basicCrudDao.findById(1);

            Assert.assertEquals(iTable.getValue(NUMBER_OF_FIRST_ROW, "id").toString(),
                    String.valueOf(invitation.getId()));
            Assert.assertEquals(iTable.getValue(NUMBER_OF_FIRST_ROW, "partnerId").toString(),
                    String.valueOf(invitation.getPartnerId()));
            Assert.assertEquals(iTable.getValue(NUMBER_OF_FIRST_ROW, "code").toString(),
                    String.valueOf(invitation.getCode()));
            Assert.assertEquals(iTable.getValue(NUMBER_OF_FIRST_ROW, "dateEnd").toString(),
                    String.valueOf(invitation.getDateEnd()));
            Assert.assertEquals(iTable.getValue(NUMBER_OF_FIRST_ROW, "status").toString(),
                    String.valueOf(invitation.getStatus()));
        } catch (DataSetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveInvitation() {
        basicCrudDao.create(new Invitation(
                5, 1, "invatationCode", new Date(2019, 3, 3), "status"
        ));
        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream("app/dao/impl/invitationDataSet/save-dataset.xml"));
            ITable iTable = iDataSet.getTable(table);

            IDataSet actualDataSet = getMySqlConnection().createDataSet();
            ITable actualITAble = actualDataSet.getTable(table);

            Assertion.assertEquals(iTable, actualITAble);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateInvitation() {
        basicCrudDao.update(new Invitation(
                1, 1, "invatationCode",
                new Date(2019, 3, 3), "status"
        ));
        try {
            IDataSet iDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream(""));
            ITable iTable = iDataSet.getTable(table);

            IDataSet actualIDataSet = getMySqlConnection().createDataSet();
            ITable actualITable = actualIDataSet.getTable(table);

            Assertion.assertEquals(iTable, actualITable);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void updateInvitationException() {
        basicCrudDao.update(new Invitation(
                6, 1, "invatationCode",
                new Date(2019, 3, 3), "status"
        ));
    }
}
