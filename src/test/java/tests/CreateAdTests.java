package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static config.Config.CREATE_ITEM;
import static config.Config.GET_ITEM_BY_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateAdTests {

    // Успешное создание объявления
    @DataProvider(name = "validJsonRequests")
    public Object[][] validJsonRequests() {
        return new Object[][]{
                {432432532, "Tractor", 5000, 10, 5, 100, 200}
        };
    }
    @Test(dataProvider = "validJsonRequests")
    public void testCreateValidAd(int sellerID, String name, int price, int contacts,
                                  int likes, int viewCount, int expectedResult) {
        //Соберем в JSON
        String jsonRequest = String.format("{\"sellerID\":%d, \"name\":\"%s\", \"price\":%d, " +
                        "\"statistics\":{\"contacts\":%d, \"likes\":%d, \"viewCount\":%d}}",
                sellerID, name, price, contacts, likes, viewCount);


        given()
                .contentType(ContentType.JSON)
                .body(jsonRequest)
                .when()
                .post(CREATE_ITEM)
                .then()
                .statusCode(expectedResult)
                .extract().response();
    }

    // Сравнение полей созданного и полученного от сервиса объявления по id
    @Test(dataProvider = "validJsonRequests")
    public void testCreateAndGetAdById(int sellerID, String name, int price, int contacts,
                                       int likes, int viewCount, int expectedResult) {

        //Соберем в JSON
        String jsonRequest = String.format("{\"sellerID\":%d, \"name\":\"%s\", \"price\":%d, " +
                        "\"statistics\":{\"contacts\":%d, \"likes\":%d, \"viewCount\":%d}}",
                sellerID, name, price, contacts, likes, viewCount);

        // Создаём объявление и получаем строку status
        String statusMessage = given()
                .contentType(ContentType.JSON)
                .body(jsonRequest)
                .when()
                .post(CREATE_ITEM)
                .then()
                .statusCode(expectedResult)
                .extract()
                .path("status"); // Извлекаем строку "status"

        // Парсим id объявления из строки status
        String adId = statusMessage.replaceAll("Сохранили объявление - ", "")
                .trim();

        // Получаем объявление по этому id и проверяем поля
        Response getResponse = given()
                .when()
                .get(GET_ITEM_BY_ID, adId);
        getResponse.then()
                .statusCode(200)
                .body("sellerId[0]", equalTo(sellerID))
                .body("name[0]", equalTo(name))
                .body("price[0]", equalTo(price))
                .body("statistics.contacts[0]", equalTo(contacts))
                .body("statistics.likes[0]", equalTo(likes))
                .body("statistics.viewCount[0]", equalTo(viewCount));
    }

    // Негативные тесты на создание объявления
    @DataProvider(name = "invalidJsonRequests")
    public Object[][] invalidJsonRequests() {
        return new Object[][]{
                // Отрицательная цена
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":-10, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},

                // Отрицательные значения статистики contacts
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":-10, \"likes\":5, \"viewCount\":100}}", 400},

                // Отрицательные значения статистики likes
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":-5, \"viewCount\":100}}", 400},

                // Отрицательные значения статистики viewCount
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":-100}}", 400},


                // Неверный тип данных: name — число вместо строки
                {"{\"sellerID\":432432532, \"name\":5000, \"price\":10, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},

                // Неверный тип данных: sellerID — строка вместо числа
                {"{\"sellerID\":\"432432532\", \"name\":\"Tractor\", \"price\":10, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},
                // Неверный тип данных: price — строка вместо числа
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":\"10\", " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},

                // Неверный тип данных: statistics — строка вместо объекта
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":10, " +
                        "\"statistics\":\"invalid\"}", 400},
                // Неверный тип данных: contacts — строка вместо числа
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":\"10\", \"likes\":5, \"viewCount\":100}}", 400},
                // Неверный тип данных: likes — строка вместо числа
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":\"5\", \"viewCount\":100}}", 400},
                // Неверный тип данных: viewCount — строка вместо числа
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":\"100\"}}", 400},


                // Пропущено поле name
                {"{\"sellerID\":432432532, \"price\":10, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},
                // Пропущено поле sellerID
                {"{\"name\":\"Tractor\", \"price\":10, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5, \"viewCount\":100}}", 400},
                // Пропущено поле statistics
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000}", 400},

                // Пропущено поле contacts
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":-10, " +
                        "\"statistics\":{\"likes\":5, \"viewCount\":100}}", 400},
                // Пропущено поле likes
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"viewCount\":100}}", 400},
                // Пропущено поле viewCount
                {"{\"sellerID\":432432532, \"name\":\"Tractor\", \"price\":5000, " +
                        "\"statistics\":{\"contacts\":10, \"likes\":5}}", 400},
        };
    }

    @Test(dataProvider = "invalidJsonRequests")
    public void testCreateAdWithInvalidJson(String invalidJson, int expectedStatusCode) {
        given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .post(CREATE_ITEM)
                .then()
                .statusCode(expectedStatusCode);
    }

}

