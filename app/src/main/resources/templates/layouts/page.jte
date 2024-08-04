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
    <body class="d-flex flex-column min-vh-100">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="${NamedRoutes.mainPath()}">Анализатор страниц</a>
                <div class="navbar-nav navbar-collapse">
                    <a class="nav-link" href="${NamedRoutes.mainPath()}">Главная</a>
                    <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
                </div>
            </div>
        </nav>
        @if(page != null && page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show ${page.getFlashStyle()}" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        ${content}
        <div class="footer footer border-top py-3 mt-5 bg-light">
            <div class="container-xl text-center">
                created by
                <a href="https://github.com/dmanufriev/">Dmitry Anufriev</a>
            </div>
        </div>
    </body>
</html>