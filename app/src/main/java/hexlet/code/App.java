package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("DB_PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {

        String jdbcDatabaseUrl = System.getenv()
                .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcDatabaseUrl);
        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        return app;
    }
}
