package app.dao.impl;

import app.dao.BasicCrudDao;
import app.entities.Employee;
import app.exceptions.EntityNotFoundException;
import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import java.net.MalformedURLException;
import java.util.List;

public class EmployeeDaoImplTest extends ConnectionForTests {

    @Autowired
    private BasicCrudDao<Employee> employeeDao;

    private static final String EMPLOYEE_TABLE = "Employees";

    private static final int NUMBER_OF_FIRST_ROW = 0;

    public EmployeeDaoImplTest() {
        super("app/dao/impl/employeeDataSet/initial-dataset.xml");
    }

    @Test
    public void setUpDatabaseTest() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/initial-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);

        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void deleteById() throws Exception {
        employeeDao.deleteById(2);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/delete-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = javax.persistence.EntityNotFoundException.class)
    public void deleteWithNonExistsPrimaryKey() {
        employeeDao.deleteById(100);
    }

    @Test
    public void edit() throws Exception {
        Employee employee = new Employee(2, "Sergey","2222", "sergey@mail.ru", "SergeyPhotoUrl");

        employeeDao.update(employee);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/edit-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = HibernateOptimisticLockingFailureException.class)
    public void editWithNonExistsPrimaryKey() {
        employeeDao
                .update(new Employee(5, "Valia", "6754324567","valia@gmail.by", "ValiaPhotoUrl"));
    }

    @Test
    public void findById() throws DataSetException, MalformedURLException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/find-by-id-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);

        Employee employee = employeeDao.findById(1);

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
    public void getAll() throws DataSetException, MalformedURLException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/initial-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        List<Employee> employees = employeeDao.findAll();
        int index = NUMBER_OF_FIRST_ROW;
        for (Employee employee : employees) {
            Assert.assertEquals(expectedTable.getValue(index, "id")
                            .toString(),
                    String.valueOf(employee.getId()));
            Assert.assertEquals(expectedTable.getValue(index, "name")
                            .toString(),
                    String.valueOf(employee.getName()));
            Assert.assertEquals(expectedTable.getValue(index, "phone")
                            .toString(),
                    String.valueOf(employee.getPhone()));
            Assert.assertEquals(expectedTable.getValue(index, "email")
                            .toString(),
                    String.valueOf(employee.getEmail()));
            Assert.assertEquals(expectedTable.getValue(index, "photoUrl")
                            .toString(),
                    String.valueOf(employee.getPhotoUrl()));
            index++;
        }
    }

    @Test
    public void save() throws Exception {
        Employee employee =
                new Employee(3, "Valia", "8765435678", "valia@gmail.by","ValiaPhotoUrl");

        employeeDao.create(employee);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataSet/save-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveAlreadyExistsEntity() {
        employeeDao
                .create(new Employee(1, "Max", "0000000","max@mail.ru","MaxPhotoUrl"));
    }


    @Test
    public void update() throws Exception {
        Employee employee = new Employee(2,"Sergey", "2222", "sergey@mail.ru","SergeyPhotoUrl");
        employeeDao.update(new Employee(employee));
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(this.getClass().getClassLoader()
                        .getResourceAsStream("app/dao/impl/employeeDataset/edit-dataset.xml"));
        ITable expectedTable = expectedDataSet.getTable(EMPLOYEE_TABLE);
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(EMPLOYEE_TABLE);
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
