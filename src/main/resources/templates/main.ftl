<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Добавить новую ссылку
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="longurl" placeholder="Введите длинную ссылку" />
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list urls as url>
            <div class="card my-3" >
                <div class="card-text m-2">
                    <span>Оригинальный url: ${url.long_url}</span>
                </div>
                <div class="card-text m-2">
                    <span>Короткий url: ${url.shorturl}</span>
                </div>
                <div class="card-text m-2">
                    <span>Количество переходов: ${url.redirects}</span>
                </div>
                <div class="card-text m-2">
                    <span>Количество уникальных переходов: ${url.unique}</span>
                </div>
                <div class="card-text m-2">
                    <a href="/redirect/${url.shorturl}">Перейти по оригинальной ссылке</a>
                </div>
            </div>
        <#else >
            No urls
        </#list>
    </div>



</@c.page>