package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.NotFoundResponse;
import hexlet.code.repository.UrlRepository;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.dto.urls.UrlPage;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;
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
        var urlChecks = UrlCheckRepository.getEntities(id);
        var page = new UrlPage(url, urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashStyle(ctx.consumeSessionAttribute("flashStyle"));
        ctx.render("urls/show.jte", model("page", page));
    }

    // Создание и сохранение нового адреса
    public static void create(Context ctx) {
        try {
            var uri = new URI(ctx.formParam("url"));
            var url = uri.toURL();
            /* TODO
            if (!Pattern.matches(".\..", urlFromSite.getHost())) {
                throw new IllegalArgumentException();
            }*/
            var builder = new StringBuilder();
            builder.append(url.getProtocol()).append("://").append(url.getHost());
            if (url.getPort() > 0) {
                builder.append(":").append(url.getPort());
            }
            var urlStr = builder.toString();

            var urls = UrlRepository.search(urlStr);
            if (!urls.isEmpty()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashStyle", "alert-info");
            } else {
                UrlRepository.save(new Url(urlStr, Timestamp.from(Instant.now())));
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashStyle", "alert-success");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashStyle", "alert-danger");
            ctx.redirect(NamedRoutes.mainPath());
        }
    }

    public static void check(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var url = UrlRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            String responseString = response.getBody().toString();

            var urlCheck = new UrlCheck();
            urlCheck.setUrlId(id);
            urlCheck.setStatusCode(response.getStatus());
            urlCheck.setCreatedAt(Timestamp.from(Instant.now()));

            Document doc = Jsoup.parse(responseString);
            Elements titleTags = doc.select("head > title");
            if (!titleTags.isEmpty()) {
                urlCheck.setTitle(titleTags.getFirst().text());
            }

            Elements h1Tags = doc.select("h1");
            if (!h1Tags.isEmpty()) {
                urlCheck.setH1(h1Tags.getFirst().text());
            }

            Elements metaTags = doc.select("head > meta");
            for (var metaTag : metaTags) {
                var name = metaTag.attr("name");
                if ("description".equals(name)) {
                    urlCheck.setDescription(metaTag.attr("content"));
                    break;
                }
            }

            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashStyle", "alert-success");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashStyle", "alert-danger");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
