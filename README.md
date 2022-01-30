# url-shortener

Сервис коротких ссылок

1.Реализованые пункты: 1-8.

2.Данный сервис на первых шагах подразумевает авторизацию и регистрацию пользователя, после чего появляется главная страница сервиса,
  где есть возможность создать короткую ссылку по имеющейся длинной ссылке. После ее создания в интерактивном режиме осуществить переход
  по любой из созданных ссылок. Рядом с каждым ссылочным объектом расположены счетчики переходов и уникальных переходов. Срок жизни ссылочного
  объекта установлен в 2 минуты, после чего вся информация о нем удаляется. Так же предусмотрена возможность удалить созданную ссылку вручную.

3.Для сборки проекта необходимо:
  Cкачать исходный код в локальный репозиторий с помощбю команды: git clone https://github.com/Alexeiyaganov/url-shortener.git
  Открыть проект в среде разработки(я работал в Intelllij Idea) и создать базу данных PostgresSql c названием "Postgres", и проверить подключению к 
  проекту, логин и пароль можно изменить в application.properties. Запустить проект и перейти по имени своего локального хоста(localhost) по порту 8081
