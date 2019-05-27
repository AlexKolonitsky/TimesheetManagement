package app.dao.impl;

import app.config.beans.DaoConfig;
import app.config.beans.HibernateConfig;
import app.config.beans.PropertyConfig;
import app.dao.BasicCrudDao;
import app.entities.Employee;
import app.exceptions.EntityNotFoundException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, DaoConfig.class, PropertyConfig.class})
@TestPropertySource(locations = {"classpath:project.properties"})
public class EmployeeDaoImplTest extends DBTestCase {

    @Autowired
    private BasicCrudDao<Employee> employeeDao;

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    private static final String SCHEMA = "Timesheetmanager";
    private static final String EMPLOYEE_TABLE = "Employee";

    private static final int NUMBER_OF_FIRST_ROW = 0;

    @Before
    public void setUp() throws Exception {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                driver);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                url);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                username);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                password);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA,
                SCHEMA);
        IDataSet data = getDataSet();
        IDatabaseConnection connection = getMySqlConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, data);
    }

    private IDatabaseConnection getMySqlConnection() throws Exception {
        IDatabaseConnection connection = super.getConnection();
        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new MySqlDataTypeFactory());
        dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER,
                new MySqlMetadataHandler());
        return connection;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDatasets/initial-dataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Test
    public void setUpDatabaseTest() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDatasets/initial-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);

        IDataSet actualDataSet = getMySqlConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteById() throws Exception {
        employeeDao.deleteById(2);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDatasets/delete-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = getMySqlConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteWithNonExistsPrimaryKey() {
        employeeDao.deleteById(100);
    }

    @Test
    public void edit() throws Exception {
        Employee employee = new Employee();
        employeeDao.update(employee);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/assignmentDatasets/edit-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = getMySqlConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editWithNonExistsPrimaryKey() {
        employeeDao
                .update(new Employee(5, "Valia", "6754324567","valia@gmail.by", "ValiaPhotoUrl"));
    }

    @Test
    public void findById() throws DataSetException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/assignmentDatasets/find-by-id-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);

        Employee employee = employeeDao.findById(4);

        Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "id")
                        .toString(),
                String.valueOf(employee.getId()));
        Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "name")
                        .toString(),
                String.valueOf(employee.getName()));
        Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "phone")
                        .toString(),
                String.valueOf(employee.getPhone()));
        Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "email")
                        .toString(),
                String.valueOf(employee.getEmail()));
        Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "photoUrl")
                        .toString(),
                String.valueOf(employee.getPhotoUrl()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdWithNonExistsPrimaryKey() {
        employeeDao.findById(100);
    }

    @Test
    public void getAll() throws DataSetException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/assignmentDatasets/initial-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        List<Employee> employees = employeeDao.findAll();
        int index = NUMBER_OF_FIRST_ROW;
        for (Employee employee : employees) {
            Assert.assertEquals(expectedTable.getValue(index, "id")
                            .toString(),
                    String.valueOf(employee.getId()));
            Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "phone")
                            .toString(),
                    String.valueOf(employee.getPhone()));
            Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "email")
                            .toString(),
                    String.valueOf(employee.getEmail()));
            Assert.assertEquals(expectedTable.getValue(NUMBER_OF_FIRST_ROW, "photoUrl")
                            .toString(),
                    String.valueOf(employee.getPhotoUrl()));
            index++;
        }
    }

    @Test
    public void save() throws Exception {
        Employee employee =
                new Employee(3, "Valia", "876543567", "valia@gmail.by","valiaPhotoUrl");

        employeeDao.create(employee);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(getClass()
                        .getClassLoader()
                        .getResourceAsStream("app/dao/impl/assignmentDatasets/save-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = getMySqlConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveAlreadyExistsEntity() {
        employeeDao
                .create(new Employee(2, "Sergey", "09876123","sergey@mail.ru","SergeyPhotoUrl"));
    }
}
