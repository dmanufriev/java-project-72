@import hexlet.code.dto.MainPage
@import hexlet.code.utils.NamedRoutes
@param MainPage page

@template.layouts.page(
    page = page,
    content = @`
        <main class="flex-grow-1">
            <div class="row bg-dark p-5">
                <div class="col-md-10 col-lg-8 mx-auto text-white">
                    <h1 class="display-3 mb-0">Анализатор страниц</h1>
                    <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
                    <form class="rss-form text-body" action="${NamedRoutes.urlsPath()}" method="post">
                            <div class="row">
                                <div class="col form-floating">
                                    <input autocomplete="off" class="h-100 w-100" placeholder="Cсылка" name="url" required>
                                </div>
                                <div class="col-auto">
                                    <input class="h-100 btn btn-lg btn-primary px-sm-5" type="submit" value="Проверить" />
                                </div>
                            </div>
                    </form>
                    <p class="mt-2 mb-0 text-secondary">Пример: https://www.example.com, https://yandex.ru, https://yandex</p>
                </div>
            </div>
        </main>
    `
)