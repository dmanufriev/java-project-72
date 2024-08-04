package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlsController;
import hexlet.code.dto.MainPage;
import hexlet.code.repository.BaseRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {

        DriverManager.registerDriver(new org.postgresql.Driver());
        DriverManager.registerDriver(new org.h2.Driver());
        DriverManager.drivers().forEach(d -> log.info(d.toString()));

        String jdbcDatabaseUrl = System.getenv()
               .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        String dbms = jdbcDatabaseUrl.split(":")[1];

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcDatabaseUrl);
        String sql;
        if (dbms.equals("postgresql")) {
            hikariConfig.setUsername(System.getenv("USERNAME"));
            hikariConfig.setPassword(System.getenv("PASSWORD"));
            sql = App.readResourceFile("schema_psql.sql");
        } else {
            sql = App.readResourceFile("schema_h2.sql");
        }

        var dataSource = new HikariDataSource(hikariConfig);
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.setDataSource(dataSource);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get(NamedRoutes.mainPath(), ctx -> {
            var page = new MainPage(false, null);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashStyle(ctx.consumeSessionAttribute("flashStyle"));
            ctx.render("index.jte", model("page", page));
        });

        app.get(NamedRoutes.urlsPath(), UrlsController::index);
        app.post(NamedRoutes.urlsPath(), UrlsController::create);
        app.get(NamedRoutes.urlPath("{id}"), UrlsController::show);
        app.post(NamedRoutes.urlPathCheck("{id}"), UrlsController::check);

        return app;
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    // Чтобы при проверке приложения автотестами шаблоны подгружались из нужного места,
    // нам потребуется явно указать расположение шаблонов.
    // Для этого нужно создать инстанс движка шаблонизатора и,
    // используя ResourceCodeResolver, указать в нем путь к шаблонам
    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
