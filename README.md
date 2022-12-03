# java-explore-with-me
Template repository for ExploreWithMe project.
https://github.com/ChoosingWaifu/java-explore-with-me/pull/1

## О проекте
Приложение - афиша, с возможностью создания событий, запросов 
на участие в них, модерированием.     

Состоит их двух сервисов  
Основной сервис, включающий всю логику приложения   
и сервис статистики, сохраняющий просмотры по uri.

## Спецификация API:

Спецификация основного сервиса: [mainAPI](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-main-service-spec.json)   
Спецификация сервиса статистики : [statsAPI](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-stats-service-spec.json)

## Docker

Адреса:
Основной сервис: localhost:8080      
Сервис статистики: localhost:9090

БД:
Основной сервис: jdbc:postgresql://ewm-db:5432/ewmservice   
Сервис статистики: jdbc:postgresql://stats-db:5432/ewmstats

Для старта приложения выполните команду docker-compose up

## Технологии

Для работы с бд:   
JPA Criteria Queries,   
JPQL Queries with named parameters,
Custom repositories.

Для микросервисного взаимодействия:
RestTemplate,
BaseClient

Для чтения ip, uri:
HttpServletRequest
