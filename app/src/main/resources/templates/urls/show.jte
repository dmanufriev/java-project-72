@import hexlet.code.dto.urls.UrlPage
@import java.text.SimpleDateFormat
@import hexlet.code.utils.NamedRoutes
@param UrlPage page

@template.layouts.page(
    page = page,
    content = @`
    <main class="flex-grow-1">
        <div class="container-lg mt-5">

            <h1>Сайт: ${page.getUrl().getName()}</h1>
            <table class="table table-bordered table-hover mt-3">
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
                    <td>${new SimpleDateFormat("dd/MM/yyyy HH:mm").format(page.getUrl().getCreatedAt())}</td>
                </tr>
                </tbody>
            </table>
            <h2 class="mt-5">Проверки</h2>
            <form action="${NamedRoutes.urlPathCheck(page.getUrl().getId())}" method="post">
                <input class="btn btn-primary" type="submit" value="Запустить проверку">
            </form>
            <table class="table table-bordered table-hover mt-3">
                <thead class="text-center">
                <tr>
                    <th class="col-1">ID</th>
                    <th class="col-1">Код ответа</th>
                    <th>title</th>
                    <th>h1</th>
                    <th>description</th>
                    <th class="col-2">Дата проверки</th>
                </tr>
                </thead>
                <tbody>
                @if(page.getChecks() != null)
                    @for(var check : page.getChecks())
                        <tr>
                            <td>${check.getId()}</td>
                            <td>${check.getStatusCode()}</td>
                            <td>${check.getTitle()}</td>
                            <td>${check.getH1()}</td>
                            <td>${check.getDescription()}</td>
                            <td>${new SimpleDateFormat("dd/MM/yyyy HH:mm").format(check.getCreatedAt())}</td>
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>

        </div>
    </main>
    `
)