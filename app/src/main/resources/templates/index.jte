@import hexlet.code.dto.MainPage
@import hexlet.code.utils.NamedRoutes
@param MainPage page

@template.layouts.page(
    page = page,
    content = @`
        <form action="${NamedRoutes.urlsPath()}" method="post">
            <h1 class="text-body-emphasis">Анализатор страниц</h1>
            <p>Бесплатно проверяйте сайты на SEO пригодность</p>
            <div>
                <label>
                    <input type="text" name="url" placeholder="Ссылка" required>
                    <input type="submit" value="Проверить" />
                </label>
            </div>
            <p>Пример: https://www.example.com</p>
        </form>
    `
)