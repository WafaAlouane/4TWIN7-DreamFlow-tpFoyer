package tn.esprit.spring.kaddem;
import org.testcontainers.containers.MySQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeAll;
@SpringBootTest
public class IntegrationTest {
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll

    static void setup() {
        mysql.start();
        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
    }

    // tes tests ici
}
