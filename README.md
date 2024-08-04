# [Анализатор страниц](https://java-project-72-w8lq.onrender.com/)
[![Actions Status](https://github.com/dmanufriev/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/dmanufriev/java-project-72/actions)
[![main](https://github.com/dmanufriev/java-project-72/actions/workflows/main.yml/badge.svg)](https://github.com/dmanufriev/java-project-72/actions/workflows/main.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/dd9a305d6dc87c51f23a/maintainability)](https://codeclimate.com/github/dmanufriev/java-project-72/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/dd9a305d6dc87c51f23a/test_coverage)](https://codeclimate.com/github/dmanufriev/java-project-72/test_coverage)

Сайт, который анализирует указанные страницы на SEO пригодность. Веб-сайт разработан на базе фреймворка Javalin. 
Здесь отработаны базовые принципы построения современных сайтов на MVC-архитектуре: 
- работа с роутингом, 
- обработчиками запросов и шаблонизатором, 
- взаимодействие с базой данных PostgresQL через JDBC.

Визуальное оформление сайта реализовано при помощи библиотеки [Bootstrap](https://getbootstrap.com/).

Автоматизированное тестирование сайта строится вокруг тестов, имитирующих HTTP-запросы и проверяющих ответы вместе с данными в базе. Подобные тесты позволяют дёшево проверять работоспособность приложения и обеспечивают легкий рефакторинг в будущем.
Тестирование выполнено при помощи библиотек [JavalinTest](https://javalin.io/tutorials/testing) и [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver).

[Сайт](https://java-project-72-w8lq.onrender.com/) выложен в публичный доступ при помощи сервиса [Render](https://render.com/).
