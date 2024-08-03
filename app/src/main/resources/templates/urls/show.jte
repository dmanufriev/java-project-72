@import hexlet.code.dto.urls.UrlPage
@import java.text.SimpleDateFormat
@import hexlet.code.utils.NamedRoutes
@param UrlPage page

@template.layouts.page(
    page = page,
    content = @`
    <form action="${NamedRoutes.urlPath(page.getUrl().getId())}" method="post">
        <h2>Сайт: ${page.getUrl().getName()}</h2>
        <table class="table table-bordered">
            <tbody>
                <tr>
                    <td>ID</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${new SimpleDateFormat("MM/dd/yyyy HH:mm").format(page.getUrl().getCreatedAt())}</td>
                </tr>
            </tbody>
        </table>
        <h2>Проверки</h2>
        <input type="submit" value="Запустить проверку" />
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Код ответа</th>
                    <th>title</th>
                    <th>h1</th>
                    <th>description</th>
                    <th>Дата проверки</th>
                </tr>
            </thead>
            <tbody>
                @if(page.getChecks() != null)
                    @for(var check : page.getChecks()))
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                    @endfor
                @endif
            </tbody>
        </table>
    </form>
    `
)