package tests;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static config.Config.GET_ITEM_BY_ID;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAdByIdTests {

    // Получение объявления по валидному id
    @DataProvider(name = "adIdsValid")
    public static Object[][] adIdsValid() {
        return new Object[][]{
                {"d496995e-e935-452c-ab3a-90ee2bc3f956", 200} // Существующее объявление
        };
    }

    @Test(dataProvider = "adIdsValid")
    public void testGetAdByValidId(String adId, int expectedStatusCode) {

        Response response = given()
                .when()
                .get(GET_ITEM_BY_ID, adId);

        response.then()
                .statusCode(expectedStatusCode);

        // извлекаем ответ
        List<Map<String, Object>> ads = response.jsonPath().getList("$");

        // Проверяем, что есть хотя бы одно объявление в списке
        assertNotNull(ads, "Список объявлений не должен быть null");
        assertEquals(ads.size(), 1, "Ожидалось одно объявление, но найдено: " + ads.size());

        // Извлекаем первое объявление из списка
        Map<String, Object> ad = ads.get(0);

        // Проверяем наличие всех полей в объявлении
        assertEquals(ad.get("id"), adId, "id не совпадает");

        assertNotNull(ad.get("sellerId"), "Поле sellerId отсутствует");
        assertNotNull(ad.get("createdAt"), "Поле createdAt отсутствует");
        assertNotNull(ad.get("name"), "Поле name отсутствует");
        assertNotNull(ad.get("price"), "Поле price отсутствует");
        assertNotNull(ad.get("statistics"), "Поле statistics отсутствует");

        Map<String, Object> stats = (Map<String, Object>) ad.get("statistics");
        assertNotNull(stats.get("contacts"), "Поле contacts отсутствует в statistics");
        assertNotNull(stats.get("likes"), "Поле likes отсутствует в statistics");
        assertNotNull(stats.get("viewCount"), "Поле viewCount отсутствует в statistics");
    }

    @DataProvider(name = "adIdsInvalid")
    public static Object[][] adIdsInvalid() {
        return new Object[][]{
                // Несуществующее объявление
                {"d496995e-e935-452c-ac3a-90ee2bc3f956", 404},
                // Некорректный формат ID
                {"d496995e-e935-452c-ac3a-90ee2bc3f956-a432-f", 400},
                // Пустой ID
                {"", 404},
                // Число вместо UUID
                {"0", 400},
                // Спецсимволы вместо UUID
                {"&*!#@@$`", 400}
        };
    }

    @Test(dataProvider = "adIdsInvalid")
    public void testGetAdByInvalidId(String adId, int expectedStatusCode) {
        given()
                .when()
                .get(GET_ITEM_BY_ID, adId)
                .then().statusCode(expectedStatusCode);
    }
}

