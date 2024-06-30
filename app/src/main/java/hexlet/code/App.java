package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.Driver;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {

        DriverManager.registerDriver(new Driver());
        DriverManager.drivers().forEach(d -> log.info(d.toString()));

        String jdbcDatabaseUrl = System.getenv()
               .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        String dbms = jdbcDatabaseUrl.split(":")[1];

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcDatabaseUrl);
        if (dbms.equals("postgresql")) {
            hikariConfig.setUsername(System.getenv("USERNAME"));
            hikariConfig.setPassword(System.getenv("PASSWORD"));
        }

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.setDataSource(dataSource);

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
