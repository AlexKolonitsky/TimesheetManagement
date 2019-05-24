package app.dao.impl;

import app.config.ApplicationInitializer;
import app.config.beans.DaoConfig;
import app.config.beans.HibernateConfig;
import app.config.beans.PropertyConfig;
import app.dao.BasicCrudDao;
import app.entities.Project;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class, PropertyConfig.class, HibernateConfig.class, ApplicationInitializer.class})
public class ProjectDaoImplTest {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    protected BasicCrudDao<Project> projectBasicCrudDao;

    @Autowired
    protected SessionFactory sessionFactory;

    private static IDatabaseConnection connection;
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    private String table = "project";
    private String schema = "timesheet_dev";

    private Connection getConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        connection = new MySqlConnection(getConnection(), schema);
        IDataSet data = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\inputDb.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, data);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void findAllTest() throws SQLException, DatabaseUnitException, FileNotFoundException {
        List<Project> projects = projectBasicCrudDao.findAll();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\inputDb.xml"));
        IDataSet actualData = connection.createDataSet();
        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(expectedData.getTable(table).getRowCount(), projects.size());
    }

    @Test
    public void findByIdTest() throws SQLException, FileNotFoundException, DatabaseUnitException {
        Project project = projectBasicCrudDao.findById(1);
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\inputDb.xml"));
        IDataSet actualData = connection.createDataSet();
        String expectedTitleOfProject = (String) expectedData.getTable(table).getValue(0, "name");
        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(expectedTitleOfProject, project.getName());
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Test
    public void updateTest() throws SQLException, FileNotFoundException, DatabaseUnitException {
        Date startDate = parseDate("2019-01-19");
        Date endDate = parseDate("2020-01-19");
        projectBasicCrudDao.update(new Project(2, 22, "project_2", "logo2", startDate, endDate, 20, "002", "grey", "description2"));
        IDataSet actualData = connection.createDataSet();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\updateDb.xml"));
        Assertion.assertEquals(expectedData, actualData);
    }

    @Test
    public void createTest() throws SQLException, DatabaseUnitException, FileNotFoundException {
        Date startDate = parseDate("2019-05-01");
        Date endDate = parseDate("2020-05-01");
        projectBasicCrudDao.create(new Project(5, 55, "project_5", "logo5", startDate, endDate, 50, "005", "yellow", "description5"));
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\createDb.xml"));
        IDataSet actualData = connection.createDataSet();
        Assertion.assertEquals(expectedData, actualData);
    }

    @Test
    public void deleteByIdTest() throws SQLException, FileNotFoundException, DatabaseUnitException {
        projectBasicCrudDao.deleteById(2);
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream("D:\\Alena\\J2EE_projects\\TimesheetManagement\\src\\test\\resources\\app\\dao\\impl\\projectDataSet\\deleteDb.xml"));
        IDataSet actualData = connection.createDataSet();
        Assertion.assertEquals(expectedData, actualData);
    }
}
