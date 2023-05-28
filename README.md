# cache-rest-service
Сервис кеширования для горизонтально масштабируемого HTTP REST-сервиса

Система для учёта книг

### Особенности и технологии:
- JDK 17
- Spring Boot
- Работа с данными: 
  - PostgreSQL
  - Spring Data JPA
- Микросервисы Spring Cloud
- Basic-авторизация Spring Security
- OpenAPI 3 и Swagger ui
- Вспомогательные библиотеки:
  - Lombok
  - MapStruct

### Данные
#### Модель данных <i>Book</i>
<pre>
<i><b> id </b></i>     long
<i><b> name </b></i>   String
<i><b> author </b></i> String
<i><b> year </b></i>   String
</pre>
#### Начальное заполнение БД
Для загрузки в БД начальных значений можно использовать параметры в
[application.yml](rest-service/src/main/resources/application.yml), после чего их необходимо "деактивировать", чтобы избежать ошибок БД.
```yaml
spring:
  sql:
    init:
      mode: always
      data-locations: classpath:database/data.sql
```
Файл для заполения БД: [database/data.sql](rest-service/src/main/resources/database/data.sql)

### Swagger интерфейс
http://localhost:8080/swagger-ui/index.html
<p align=center>
    <img width= 80% src=https://github.com/Mihail-Ko/cache-rest-service/assets/98303471/fbf7ab1c-add9-4972-bfca-7f874fd4039d alt="Swagger"/>
</p>

### Микросервисы и горизонтальное масштабирование
- #### api-gateway
Шлюз между пользователем и запущенными сервисами (модуль rest-service): [localhost:8080](http://localhost:8080)
- #### discovery-service
Панель Eureka-сервера: [localhost:8081](http://localhost:8081)
- #### rest-service
Для реализации горизонтального масштабирования необходимо запустить несколько экземпляров.
<p align=center>
    <img width=500px src=https://github.com/Mihail-Ko/cache-rest-service/assets/98303471/6df0978b-d4aa-4f82-8775-6c28d2928c00 alt="Схема"/>
</p>
<p align=center> Схема микросервисов </p>
<p align=center>
    <img src=https://github.com/Mihail-Ko/cache-rest-service/assets/98303471/787ff978-470d-492c-87a3-a452e17ba8c8 alt="Eureka server"/>
</p>
<p align=center> Панель Spring Eureka </p>

### Обработка исключений
Реализована централизованная обработка исключений классом [DefaultAdvice](rest-service/src/main/java/com/example/restservice/exception/DefaultAdvice.java)

### Эмуляция ресурсоёмкости
Настраиваемые задержки в методах формирования данных для демонстрации быстродействия получения данных из кэша.

[application.yml](rest-service/src/main/resources/application.yml)
```yaml
delay:
  getOne: 2000
  getAll: 3000
  delete: 2000
  update: 2000
  add: 100
  ```

[CustomBookRepositoryImpl.java](rest-service/src/main/java/com/example/restservice/repository/CustomBookRepositoryImpl.java)
```java
    private void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException interruptedExc) {
            interruptedExc.printStackTrace();
        }
    }
```