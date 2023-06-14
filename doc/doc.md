# Методика испытаний
#### Количество экземпляров при масштабировании - от 1 до 3.
| Наименование проверки                                                      | Выполняемые действия                                                                                                                                                              | Результат                                                                          |
|:---------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| Совместный запуск модулей                                                  | 1. Запуск discovery-service <br> 2. Запуск rest-service <br> 3. Запуск api-gateway                                                                                                | Успешно                                                                            |
| Горизонтальное масштабирование - отказоустойчивость                        | 1. Запуск 3 экземпляров rest-service, ожидание обнаружения шлюзом <br> 2. Отключение экземпляров, кроме одного; отправка запросов                                                 | 1. Успешно <br> 2. Корректная работа системы                                       |
| Горизонтальное масштабирование                                             | 1. Запуск 3 экземпляров rest-service, ожидание обнаружения шлюзом <br> 2. Отправка нескольких запросов                                                                            | Запросы распределены между экземплярами: в логах каждого экзепляра разные запросы  |
| Горизонтальное масштабирование для шлюза                                   | 1. Запуск 2 экземпляров api-gateway <br> 2. Отправка запросов на единый домен <br> 3. Отключение одного шлюза <br> 4. Повторная отправка запросов                                 | Корректная работа системы; успешность запросов зависит от клиента                  |
| Подключение Open API                                                       | Открытие страницы интерфейса Swagger /swagger-ui/index.html                                                                                                                       | Графический интерфейс Swagger                                                      |
| Авторизация                                                                | 1. Отправка GET запроса без заголовков basic-авторизации <br> 2. Отправка GET запроса с заголовком basic-авторизации с логином и паролем, указанными в настройках Spring Security | 1. Код 401 <br> 2. Код 200                                                         |
| CSRF-токен                                                                 | POST/DELETE/PUT-запросы с поддержкой CSRF-токена                                                                                                                                  | - Токен получен при авторизации <br> - хранится в cookie <br> - запросы успешные   |
| Получение несуществующей книги                                             | GET-запрос /book/100                                                                                                                                                              | Код 404, сообщение "Элемент не найден"                                             |
| Получение книги из базы                                                    | GET-запрос /book/5                                                                                                                                                                | Код 200, получен объект                                                            |
| Получение страницы книг с 0 параметром                                     | GET-запрос /book?page=0                                                                                                                                                           | Код 400, сообщение "Номер страницы должен быть больше 0"                           |
| Получение страницы книг с нечисловым параметром                            | GET-запрос /book?page=a                                                                                                                                                           | Код 400, сообщение "Ошибка в параметре запроса"                                    |
| Получение существующей страницы книг                                       | GET-запрос /book?page=1                                                                                                                                                           | Код 200, получен список объектов                                                   |
| Обновление данных несуществующей книги                                     | PUT-запрос /book , в теле указан id несуществующей книги                                                                                                                          | Код 404, сообщение "Элемент не найден"                                             |
| Неверный формат данных в теле                                              | PUT-запрос, POST-запрос /book , в теле указан неверный формат полей                                                                                                               | Код 400, сообщение "Ошибка в теле запроса"                                         |
| Обновление данных книги                                                    | PUT-запрос /book , поля изменены/не изменены                                                                                                                                      | Код 200, получен обновленный объект                                                |
| Удаление несуществующей книги                                              | DELETE-запрос /book/100                                                                                                                                                           | Код 404, сообщение "Элемент не найден"                                             |
| Удаление книги                                                             | DELETE-запрос /book/5                                                                                                                                                             | Код 200, сообщение "Удалена книга с id 5"                                          |
| Создание книги                                                             | POST-запрос /book поля заполнены/незаполнены                                                                                                                                      | Код 200, получен созданный объект                                                  |                                                                                                                  |                                                          ||                                                 |                                                                                                                  |                                                                                  |
| Ошибка сервера                                                             | Вызов Exception в выполняемой функции                                                                                                                                             | Код 500, трассировка стека в логах, сообщение "Ошибка сервера"                     |
| Одинаковые запросы на получение данных из БД могут выполняться параллельно | Выполнено несколько одновременных одинаковых запросов на получение данных для незакэшированного объекта                                                                           | В логах присутствуют запросы от клиента, но обращение к БД происходит только 1 раз |


### Кэш
| Наименование проверки       | Выполняемые действия                                                                                                                                        | Результат                                                                                                      |
|:----------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------|
| Получение книг из БД        | Впервые отправленные GET-запросы /book/5 , /book?page=1                                                                                                     | Задержка при получении данных, факт обращения к базе данных в логах                                            |
| Получение книг из кэша      | GET-запросы /book/5 , /book?page=1                                                                                                                          | Отсутствие заметной задержки, отсутствие записи об обращении к базе данных в логах                             |
| Обновление кэша книги       | 1. PUT-запрос /book , объект кэширован (id = 5) <br> 2. GET-запрос /book/5                                                                                  | 1. Задержка при проверке наличия объекта в БД перед обновлением <br> 2. Получение обновленного объекта из кэша |
| Удаление книги из кэша      | 1. DELETE-запрос /book/5 <br> 2. GET-запрос /book/5                                                                                                         | Обращение к базе данных, сообщение об отсутствии значения                                                      |
| Обновление кэша страниц     | 1. GET-запрос /book?page=1 <br> 2. DELETE/POST/PUT-запрос - сброс кэша страниц <br> 3. GET-запрос /book?page=1                                              | 1. Обращение к базе данных <br> 3. Обращение к базе данных                                                     |
| Сброс значений метедом POST | 1. POST-запрос /book/cache <br/> 2. GET-запросы                                                                                                             | 1. Код 200 <br> 2. задержка при получении даннных                                                              |
| Время жизни кэша            | 1. Задано время жизни кэша 15 сек <br/> 2. GET-запрос /book?page=1 при отсутствующем кэше <br> 3. Отправлен повторный запрос через 10 секунд, затем через 5 | Через 10 секунд данные берутся из кэша, ещё через 5 происходит обращение к БД                                  |