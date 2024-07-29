@import hexlet.code.dto.BasePage
@import gg.jte.Content
@import hexlet.code.utils.NamedRoutes

@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Анализатор страниц</title>
    </head>
    <body>
        <div class="menu">
            <p>
                <a href="${NamedRoutes.mainPath()}">Анализатор страниц</a>
                <a href="${NamedRoutes.mainPath()}">Главная</a>
                <a href="${NamedRoutes.urlsPath()}">Сайты</a>
            </p>
        </div>
        @if(page != null && page.getFlash() != null)
            <div class="alert ${page.getFlashStyle()}" role="alert">
                ${page.getFlash()}
            </div>
        @endif
        ${content}
        <div class="footer">
            <p>created by <a href="https://github.com/dmanufriev/">Dmitry Anufriev</a></p>
        </div>
    </body>
</html>