import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class GetSingleUserNotFoundTest extends BaseTest {

    @DisplayName("GET for single user with wrong id")
    @Description("Make GET request for single user with wrong id and check json in response is empty and status code is equal 404")
    @Link("http://jira.com/test-12345")
    @Feature("API tests")
    @Owner("Baranov K.M.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void singleUserNotFound() {
        int wrongUserId = 50;

        given()
            .spec(getDefaultRequestSpecification())
        .when()
            .get(USERS_PATH_WITH_PARAMS, wrongUserId)
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HTTP_NOT_FOUND)
            .body(Matchers.is(EMPTY_JSON));
    }
}
