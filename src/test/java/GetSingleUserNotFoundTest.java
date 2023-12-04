import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class GetSingleUserNotFoundTest extends BaseTest {

    @Test
    @DisplayName("GET for single user with wrong id")
    @Description("Make GET request for single user with wrong id and check json in response is empty and status code is equal 404")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(GET_SINGLE_USER_ERRORS)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void singleUserNotFound() {
        int wrongUserId = 50;

        Allure.step("Make GET request with wrong id");
        Response response = given()
            .spec(getDefaultRequestSpecification())
        .when()
            .get(USERS_PATH_WITH_PARAMS, wrongUserId);

        Allure.step("Check status code 404 and json is empty");
        response
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HTTP_NOT_FOUND)
            .body(Matchers.is(EMPTY_JSON));
    }
}
