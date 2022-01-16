#### Запуск

1. В `src\main\resources\application.yaml` заменить токен на свой:
`token: ${PASTE_YOUR_TOKEN_HERE}`
2. Выполнить команду 
`$ mvn clean install`
3. Запустить приложение

### Проверка работоспособности
* После запуска приложения перейти в сваггер по урлу: http://localhost:8080/swagger-ui/#/
* Если страница открылась - приложение работает. 
* Можно протестировать любой end-point.

#### Starting

1. In `src\main\resources\application.yaml` replace the token with your own:
`token: ${PASTE_YOUR_TOKEN_HERE}`
2. Run command
`$ mvn clean install`
3. Launch the application

### Health check
* After launching the application, go to the swagger by url: http://localhost:8080/swagger-ui/#/
* If the page is opened - the application is working.
* You can test any end-point.
