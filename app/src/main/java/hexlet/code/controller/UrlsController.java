package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.NotFoundResponse;
import hexlet.code.repository.UrlRepository;
import hexlet.code.dto.urls.UrlsPage;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    // Список адресов
    public static void index(Context ctx) throws SQLException {
        var urlsList = UrlRepository.getEntities();
        var page = new UrlsPage(urlsList);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashStyle(ctx.consumeSessionAttribute("flashStyle"));
        ctx.render("urls/index.jte", model("page", page));
    }

    // Страница адреса
    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        ctx.render("urls/show.jte", model("url", url));
    }

    // Создание и сохранение нового адреса
    public static void create(Context ctx) throws SQLException {
        try {
            var name = ctx.formParamAsClass("url", String.class)
                    .check(value -> value.length() > 0, "Не заполнено поле")
                    .get().trim();
            var urls = UrlRepository.search(name);
            if (!urls.isEmpty()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashStyle", "alert-info");
            } else {
                var url = new Url(name, Timestamp.from(Instant.now()));
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashStyle", "alert-success");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (ValidationException ex) {
            // TODO
        }
    }
}
