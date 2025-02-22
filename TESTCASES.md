# Общие предусловия для всех тестов:
1. Сервер API доступен по адресу: `https://qa-internship.avito.com`.
2. Пользователь имеет права на создание и получение объявлений.

# Тест-кейсы для /api/1/item
## 1. Успешное создание объявления
Цель: проверка корректного создания объявления.  
Шаги:  
•	Отправить POST-запрос с валидным JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: объявление успешно создается, статус-код 200.
## 2. Получение созданного объявления по ID и проверка корректности сохранения полей
Цель: проверка, что полученные данные совпадают с отправленными при создании.  
Шаги:  
•	Создать объявление (POST-запрос /api/1/item):  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
•	Убедиться, что в ответ пришло id объявления.  
•	Получить от сервера объявление по ID (GET-запрос /api/1/item/{id}).  
•	Проверить, что поля sellerId, name, price, statistics.contacts, statistics.likes, statistics.viewCount в полученном объявлении соответствуют отправленным данным.  
Ожидаемый результат: все поля совпадают.
## 3. Негативные тесты на создание объявления
### 3.1. Отрицательные значения
Цель: проверка, что система не принимает отрицательные значения в числовых полях. 
#### 3.1.1 Отрицательная поле price
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":-10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: объявление не создается, статус-код 400.  
#### 3.1.2 Отрицательное поле contacts
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":-10, "likes":5, "viewCount":100}}  
Ожидаемый результат: объявление не создается, статус-код 400.  
#### 3.1.3 Отрицательное поле likes
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":-5, "viewCount":100}}  
Ожидаемый результат: объявление не создается, статус-код 400.  
#### 3.1.4 Отрицательное поле viewCount
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":-100}}  
Ожидаемый результат: объявление не создается, статус-код 400.  
### 3.2. Неверные типы данных
Цель: проверка, что система отклоняет запросы с неверными типами данных.   
#### 3.2.1 Число вместо строки в name
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":500, "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.2.2 Строка вместо числа в sellerId
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":"432432532", "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.2.3 Строка вместо числа в price
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price": "10", "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.2.4 Строка вместо объекта в statistics
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":"invalid":}  
Ожидаемый результат: статус-код 400.  
#### 3.2.5 Строка вместо числа в contacts
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":"10", "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.2.6 Строка вместо числа в likes
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":"5", "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.2.7 Строка вместо числа в viewCount
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":"100"}}  
Ожидаемый результат: статус-код 400.  
### 3.3. Пропущенные поля
Цель: проверка обработки запросов с отсутствующими полями.  
#### 3.3.1 Отсутствует поле name
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.3.2 Отсутствует поле sellerID
Шаги:  
•	Отправить POST-запрос с JSON:  
{"name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.3.3 Отсутствует поле statistics
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10}  
Ожидаемый результат: статус-код 400.  
#### 3.3.4 Отсутствует поле contacts
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{ "likes":5, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.3.5 Отсутствует поле likes
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "viewCount":100}}  
Ожидаемый результат: статус-код 400.  
#### 3.3.6 Отсутствует поле viewCount
Шаги:  
•	Отправить POST-запрос с JSON:  
{"sellerID":432432532, "name":"Tractor", "price":10, "statistics":{"contacts":10, "likes":5}}  
Ожидаемый результат: статус-код 400.  

# Тест-кейсы для /api/1/item/{id}
## 1. Получение объявления по валидному ID
Цель: проверка получения существующего объявления по корректному ID.   
Шаги:  
•	Отправить GET-запрос с валидным ID:  
d496995e-e935-452c-ab3a-90ee2bc3f956  
•	Проверить, что статус-код ответа 200.  
•	Проверить, что возвращено 1 объявление  
•	Проверить, что id соответствует id из запроса  
•	Проверить, что объявление содержит все поля: sellerId, createdAt, name, price, statistics.contacts, statistics.likes, statistics.viewCount  
Ожидаемый результат: объявление найдено, статус-код 200, все поля присутствуют.  
## 2. Негативные тесты на получение объявления по UUID
### 2.1. Несуществующее объявление
Цель: проверка, что запрос к несуществующему ID возвращает ошибку.   
Шаги:  
•	Отправить GET-запрос с несуществующим UUID:  
d496995e-e935-452c-ab3a-90ee2bc3f956  
Ожидаемый результат: статус-код 404.  
### 2.2. Некорректный формат UUID
Цель: проверка, что запрос с некорректным UUID отклоняется.   
#### 2.2.1 UUID содержит лишние символы
Шаги:  
•	Отправить GET-запрос с некорректным UUID:  
d496995e-e935-452c-ac3a-90ee2bc3f956-a432-f  
Ожидаемый результат: статус-код 400  
#### 2.2.2 UUID пуст
Шаги:  
•	Отправить GET-запрос с пустой строкой вместо UUID: “”  
Ожидаемый результат: статус-код 404  
#### 2.2.3 Число вместо UUID
Шаги:  
•	Отправить GET-запрос с числом вместо UUID:  
“0”  
Ожидаемый результат: статус-код 400  
#### 2.2.4 Спецсимволы вместо UUID
Шаги:  
•	Отправить GET-запрос с спец.символами вместо UUID:  
“&*!#@@$`”  
Ожидаемый результат: статус-код 400  


# Тест-кейсы для /api/1/statistic/{id}
## 1. Получение статистики по валидному UUID
Цель: проверка корректного получения статистики по существующему ID объявления.   
Шаги:  
•	Отправить GET-запрос с валидным UUID 0cd4183f-a699-4486-83f8-b513dfde477a  
•	Проверить, что статус-код 200.  
•	Проверить наличие и корректность всех полей (likes, viewCount, contacts).  
Ожидаемый результат: статистика корректно возвращается, статус-код 200.  
## 2. Негативные тесты на получение статистики
### 2.1. Несуществующий ID
Цель: проверка обработки запроса с несуществующим ID объявления.   
Шаги:  
•	Отправить GET-запрос с несуществующим UUID:  
d496995e-e935-452c-ac3a-90ee2bc3f956  
Ожидаемый результат: статус-код 404.  
### 2.2. Некорректный формат ID
Цель: проверка обработки некорректных ID.   
#### 2.2.1 UUID содержит лишние символы
Шаги:  
•	Отправить GET-запрос с неверным форматом UUID:  
d496995e-e935-452c-ac3a-90ee2bc3f956-fd3455  
Ожидаемый результат: статус-код 400.  
#### 2.2.2 UUID содержит пустую строку
Шаги:
•	Отправить GET-запрос с пустой строкой вместо UUID: “”  
Ожидаемый результат: статус-код 404.  
#### 2.2.3 UUID равен нулю
Шаги:  
•	Отправить GET-запрос с UUID = 0:  
Ожидаемый результат: статус-код 400.  
#### 2.2.4 UUID содержит отрицательное число
Шаги:  
•	Отправить GET-запрос с неверным форматом UUID:   
-100  
Ожидаемый результат: статус-код 400.  
#### 2.2.5 UUID содержит число, превышающее Long
Шаги:  
•	Отправить GET-запрос с неверным форматом UUID:  
9999999999999999999  
Ожидаемый результат: статус-код 400.  
#### 2.2.6 UUID содержит специальные символы
Шаги:  
•	Отправить GET-запрос с неверным форматом UUID:  
“&*!#@@$`”  
Ожидаемый результат: статус-код 400.  

# Тест-кейсы для /api/1/{sellerID}/item
## 1. Получение объявлений по валидному sellerID
Цель: проверка корректного получения объявлений по существующему sellerID.   
Шаги:  
•	Отправить GET-запрос с валидным sellerID:  
9564386543  
•	Проверить, что статус-код 200.  
•	Проверить число объявлений равно 1.  
•	Проверить наличие всех полей: sellerId, createdAt, id, name, price, statistics.contacts, statistics.likes, statistics.viewCount   
Ожидаемый результат: объявления успешно получены, все поля присутствуют.  
## 2. Получение объявлений по sellerID, у которого нет объявлений
Цель: проверка, что запрос к sellerID без объявлений возвращает пустой список.   
Шаги:  
•	Отправить GET-запрос с валидным sellerID без объявлений:  
2419423791  
•	Проверить, что статус-код 200.  
•	Проверить, что список объявлений пуст.   
Ожидаемый результат: пустой список объявлений, статус-код 200.  
## 3. Негативные тесты на получение объявлений по sellerID
### 3.1. Некорректный sellerID
Цель: проверка обработки запроса с некорректным sellerID.   
#### 3.1.1 Число больше long как sellerID
Шаги:  
•	Отправить GET-запрос с sellerID:  
9999999999999999999  
Ожидаемый результат: статус-код 400.  
#### 3.1.2 Отрицательное число как sellerID
Шаги:  
•	Отправить GET-запрос с sellerID:  
-9999999999999999999  
Ожидаемый результат: статус-код 400.  
#### 3.1.3 Нуль как sellerID
Шаги:  
•	Отправить GET-запрос с sellerID:  
0  
Ожидаемый результат: статус-код 400.  
#### 3.1.4 Длинная строка как sellerID
Шаги:  
•	Отправить GET-запрос с sellerID:  
“9999999999999999999”  
Ожидаемый результат: статус-код 400.  
#### 3.1.5 Пустая строка как sellerID
Шаги:  
•	Отправить GET-запрос с sellerID:  
“”  
Ожидаемый результат: статус-код 405.  
#### 3.1.6 Спецсимволы как sellerID Шаги:
•	Отправить GET-запрос с sellerID:  
“ &*!#@@$`”  
Ожидаемый результат: статус-код 400.  

