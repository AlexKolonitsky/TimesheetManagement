package endToEndTests;

import app.config.ApplicationInitializer;
import app.config.beans.DaoConfig;
import app.config.beans.HibernateConfig;
import app.config.beans.PropertyConfig;
import app.entities.Timesheet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationInitializer.class, PropertyConfig.class, HibernateConfig.class, DaoConfig.class})

public class TimesheetResourcesE2ETests  {


    private final String BASEURL = "http://localhost:8080/Timesheet";
    private final Timesheet TIMESHEET_POST = new Timesheet(5,5, "string", "ok");
    private final Timesheet TIMESHEET_PUT = new Timesheet(1,8, "putMeth", "notOk");

    private static IDatabaseConnection connection;

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

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
        connection = new MySqlConnection(getConnection(), "timesheet_dev");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("timesheetDataSet\\initialDataset.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    public void getMethod() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl(BASEURL).responseTimeout(Duration.ofMillis(30000)).build();
        client.get()
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Timesheet.class);
    }

    @Test
    public void postMethod() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl(BASEURL).responseTimeout(Duration.ofMillis(30000)).build();
        client.post()
                .body(BodyInserters.fromObject(TIMESHEET_POST))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void deleteMethod() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl(BASEURL).responseTimeout(Duration.ofMillis(30000)).build();
        client.delete()
                .uri("/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void putMethod() {
        String uri = String.valueOf(TIMESHEET_PUT.getId());
        WebTestClient client = WebTestClient.bindToServer().baseUrl(BASEURL).responseTimeout(Duration.ofMillis(30000)).build();
        client.put()
                .uri("/" + uri)
                .body(BodyInserters.fromObject(TIMESHEET_PUT))
                .exchange()
                .expectStatus().isCreated();
    }

}
