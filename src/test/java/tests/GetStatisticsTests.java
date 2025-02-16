package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static config.Config.GET_STATISTICS_BY_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetStatisticsTests {

    // Получение статистики объявления по валидному id
    @DataProvider(name = "adIdsValid")
    public static Object[][] sellerIdsValid() {
        return new Object[][]{
                {"0cd4183f-a699-4486-83f8-b513dfde477a", 246, 258, 3, 200}
        };
    }

    @Test(dataProvider = "adIdsValid")
    public void testGetStatisticsByValidId(String adId, int likeCount, int viewCount,
                                           int contactCount, int expectedStatusCode) {

        given()
                .when()
                .get(GET_STATISTICS_BY_ID, adId)
                .then()
                .statusCode(expectedStatusCode)
                // Проверка наличия полей в объявлении
                .body("likes[0]", equalTo(likeCount))
                .body("viewCount[0]", equalTo(viewCount))
                .body("contacts[0]", equalTo(contactCount));
    }

    // Получение статистики объявления по невалидному id
    @DataProvider(name = "adIdsInvalid")
    public static Object[][] sellerIdsInvalid() {
        return new Object[][]{
                // несуществующий uuid
                {"d496995e-e935-452c-ac3a-90ee2bc3f956", 404},
                // неверный формат id
                {"d496995e-e935-452c-ac3a-90ee2bc3f956-fd3455", 400},
                // пустой id
                {"", 404},
                // 1 символ id
                {"1", 400},
                // длинный id
                {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 400},
                // Число больше long
                {new BigInteger("9999999999999999999"), 400},
                // Отрицательный ID
                {new BigInteger("-1"), 400},
                // ID = 0
                {0, 400},
                // Спецсимволы
                {"&*!#@@$`", 400},
        };
    }

    @Test(dataProvider = "adIdsInvalid")
    public void testGetStatisticsByInvalidId(Object adId, int expectedStatusCode) {
        given()
                .when()
                .get(GET_STATISTICS_BY_ID, adId)
                .then()
                .statusCode(expectedStatusCode);
    }
}

