# cache-rest-service
- Сервис кеширования для горизонтально масштабируемого HTTP REST-сервиса
- Система для учёта книг

### Особенности и технологии:
- JDK 17
- Spring Boot
- PostgreSQL
- Микросервисы Spring Cloud
- Basic-авторизация Spring Security
- OpenAPI 3 и Swagger ui
- Кэш Hazelcast
- Вспомогательные библиотеки:
  - Lombok
  - MapStruct

### Сборка и запуск
```
mvn clean package
```
Eureka-сервер:
```
java -jar discovery-service\target\discovery-service-0.0.1-SNAPSHOT.jar
```
Масштабируемый REST-сервис:
```
java -jar rest-service\target\rest-service-0.0.1-SNAPSHOT.jar
```
Gateway:
```
java -jar api-gateway\target\api-gateway-0.0.1-SNAPSHOT.jar
```
Для запуска другого экземпляра Gateway:
```
java -jar api-gateway\target\api-gateway-0.0.1-SNAPSHOT.jar --server.address=127.0.0.3
```
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
http://127.0.0.2/swagger-ui/index.html
<p align=center>
    <img width= 80% src=https://github.com/Mihail-Ko/cache-rest-service/assets/98303471/fbf7ab1c-add9-4972-bfca-7f874fd4039d alt="Swagger"/>
</p>

### Микросервисы и горизонтальное масштабирование
- #### api-gateway
Прокси между пользователем и запущенными сервисами: http://127.0.0.2/
- #### discovery-service
Регистрация микросервисов.

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
Настраиваемые задержки в для демонстрации быстродействия получения данных из кэша.

[application.yml](rest-service/src/main/resources/application.yml)
```yaml
delay:
  getOne: 1500
  getAll: 3000
  delete: 1500
  update: 1500
  insert: 500
  ```

[CustomBookRepository.java](rest-service/src/main/java/com/example/restservice/repository/CustomBookRepository.java)
```java
    private void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException interruptedExc) {
            interruptedExc.printStackTrace();
        }
    }
```
### Кэширование
В [hazelcast.yml](rest-service/src/main/resources/hazelcast.yml) включена синхронизация и установлено время жизни кэша.
<p align=center>
    <img src=https://github.com/Mihail-Ko/cache-rest-service/assets/98303471/27e8c775-c69e-44ee-b5f9-0bc711f54132 alt="Hazelcast network"/>
</p>
<p align=center> Hazelcast синхронизация </p>
