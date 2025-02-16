package tests;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static config.Config.GET_ITEMS_BY_SELLER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAdsBySellerIdTests {

    // Получение списка объявлений по валидному sellerID
    @DataProvider(name = "sellerIdsValid")
    public static Object[][] sellerIdsValid() {
        return new Object[][]{
                // Продавец с объявлениями
                {9564386543L, 200, 1},
                // Продавец без объявлений
                {2419423791L, 200, 0},
        };
    }

    @Test(dataProvider = "sellerIdsValid")
    public void testGetAdsBySellerValid(long sellerID,
                                        int expectedStatusCode, int adCount) {

        Response response = given()
                .when()
                .get(GET_ITEMS_BY_SELLER, sellerID);

        response.then()
                .statusCode(expectedStatusCode)
                .body("size()", equalTo(adCount));

        List<Map<String, Object>> ads = response.jsonPath().getList("$");

        // Проверяем, что список объявлений содержит нужное количество записей
        assertEquals(ads.size(), adCount);

        // Проверяем наличие всех полей
        for (Map<String, Object> ad : ads) {
            assertEquals(ad.get("sellerId"), sellerID, "sellerId не совпадает");

            assertNotNull(ad.get("createdAt"), "createdAt отсутствует");
            assertNotNull(ad.get("id"), "id отсутствует");
            assertNotNull(ad.get("name"), "name отсутствует");
            assertNotNull(ad.get("price"), "price отсутствует");
            assertNotNull(ad.get("statistics"), "statistics отсутствует");

            Map<String, Object> stats = (Map<String, Object>) ad.get("statistics");
            assertNotNull(stats.get("contacts"), "contacts отсутствует");
            assertNotNull(stats.get("likes"), "likes отсутствует");
            assertNotNull(stats.get("viewCount"), "viewCount отсутствует");
        }
    }


    // Получение списка объявлений по невалидному sellerID
    @DataProvider(name = "sellerIdsInvalid")
    public static Object[][] sellerIdsInvalid() {
        return new Object[][]{
                // Число слишком большое
                {new BigInteger("9999999999999999999"), 400},
                // Отрицательный ID
                {new BigInteger("-1"), 400},
                // ID = 0
                {0, 400},
                // Длинная строка
                {"111111111111111111111111111111111111111111111", 400},
                // Пустая строка
                {"", 405},
                // Спецсимволы
                {"&*!#@@$`", 400},
        };
    }

    @Test(dataProvider = "sellerIdsInvalid")
    public void testGetAdsByInvalidSellerId(Object sellerId, int expectedStatusCode) {
        given()
                .when()
                .get(GET_ITEMS_BY_SELLER, sellerId)
                .then()
                .statusCode(expectedStatusCode);
    }
}

