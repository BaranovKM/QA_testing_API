import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserTest extends BaseTest {

    @Test
    @DisplayName("DELETE for single user")
    @Description("Make DELETE request for single user and check json in response is empty")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(REMOVE_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void deleteUserTest() {
        String deletedPersonId = "10";

        Allure.step("Make DELETE request");
        Response response = given()
                .spec(getDefaultRequestSpecification())
                .when()
                .delete(USERS_PATH_WITH_PARAMS, deletedPersonId)
                .then()
                .statusCode(HTTP_NO_CONTENT)
                .extract()
                .response();

        Allure.step("Check response body is empty");
        assertThat(response.asString()).isEmpty();

    }
}
