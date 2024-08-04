@import hexlet.code.dto.urls.UrlsPage
@import java.text.SimpleDateFormat
@import hexlet.code.utils.NamedRoutes
@param UrlsPage page

@template.layouts.page(
    page = page,
    content = @`
    <main class="flex-grow-1">
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead class="text-center">
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
                <tbody class="text-center">
                @for(var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                        @if(url.getCheckedAt() != null)
                            <td>${new SimpleDateFormat("dd/MM/yyyy HH:mm").format(url.getCheckedAt())}</td>
                            <td>${url.getStatusCode()}</td>
                        @else
                            <td></td>
                            <td></td>
                        @endif
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    </main>
    `
)