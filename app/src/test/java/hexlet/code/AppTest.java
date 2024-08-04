package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class AppTest {
    Javalin app;
    static MockWebServer mockServer;

    @BeforeAll
    public static final void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        String mockResponse = Files.readString(Paths.get("src/test/resources/mockTest.html"));
        mockServer.enqueue(new MockResponse().setBody(mockResponse));
        mockServer.start();
    }

    @AfterAll
    public static final void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.mainPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Сайты");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://test.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://test.com");
        });
    }

    @Test
    public void testCreateIncorrectUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=www.test.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateSameUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://test.com", Timestamp.from(Instant.now()));
            UrlRepository.save(url);
            var requestBody = "url=https://test.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://test.com");
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://test.com", Timestamp.from(Instant.now()));
            UrlRepository.save(url);
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(url.getName());
        });
    }

    @Test
    public void testUrlPageNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath((long) 9999));
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheckUrlPage() {
        JavalinTest.test(app, (server, client) -> {

            String mockUrl = mockServer.url("/test").toString();
            Url url = new Url(mockUrl, Timestamp.from(Instant.now()));
            UrlRepository.save(url);

            var response = client.post(NamedRoutes.urlPathCheck(url.getId())).body().string();
            assertThat(response).contains(mockUrl);
            assertThat(response).contains("Description for MockWebServer");
            assertThat(response).contains("Title for MockWebServer");
            assertThat(response).contains("H1 for MockWebServer");

        });
    }
}
