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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>Анализатор страниц</title>
    </head>
    <body>
        <div class="menu p-3 text-bg-dark">
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
        <div class="footer d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
            <p>created by <a href="https://github.com/dmanufriev/">Dmitry Anufriev</a></p>
        </div>
    </body>
</html>