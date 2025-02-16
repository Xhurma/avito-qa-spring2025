# Руководство по установке и запуску проекта

## 1. Клонирование репозитория
Скачайте репозиторий с GitHub

## 2. Проверка установки Java
Проект требует установленную Java (JDK 17 или выше).
Проверьте версию Java и Maven:
```sh
java -version
mvn -version
```

## 3. Установка и сборка проекта с Maven

Соберите проект с помощью команды:
```sh
mvn clean install
```

## 4. Запуск тестов
Запустите все тесты с помощью Maven:
```sh
mvn test
```
Если нужно запустить тесты определенного класса, используйте команду:
```sh
mvn -Dtest=<ИмяКлассаТеста> test
```

## Структура проекта
```
src/
 ├── test/
 │   ├── java/
 │   │   ├── config/ – содержит конфигурационные файлы
 │   │   │   ├── Config.java - задаёт URL для использования API
 │   │   ├── utils/
 │   │   │   ├── CreateJson.java - для создания Json файла
 │   │   ├── tests/ – содержит тестовые класс
 │   │   │   ├── CreateAdTests.java – тесты для создания объявлений.
 │   │   │   ├── GetAdByIdTests.java – тесты для получения объявления по UUID.
 │   │   │   ├── GetAdsBySellerIdTests.java – тесты для получения списка объявлений по sellerId.
 │   │   │   ├── GetStatisticsTests.java – тесты для получения статистики объявления по UUID.
 ├── README.md – общее описание проекта.
 ├── BUGS.md – список обнаруженных багов.
 ├── TESTCASES.md – описание тест-кейсов.

 ├── TASK1.md – решение первого задания.
```



