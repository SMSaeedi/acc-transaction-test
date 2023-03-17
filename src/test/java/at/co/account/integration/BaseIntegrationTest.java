package at.co.account.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BaseIntegrationTest {
    public static final String CONTEXT_PATH = "/api";
    public static final String SERVER_URI = "http://localhost:9856/cap-gem";
    protected WebTestClient webTestClient;

    public BaseIntegrationTest() {
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl(SERVER_URI)
                .responseTimeout(Duration.ofMillis(50000))
                .build();
    }

}
