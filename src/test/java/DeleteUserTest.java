import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

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
    @Feature(USER_CREATION)
    @Story(ENDPOINT_FOR_REMOVING_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void deleteUserTest() {
        String deletedPersonId = "10";

        Allure.step("Make DELETE request");
        RequestSpecification request = given().spec(getDefaultRequestSpecification());

        Response response = request
                .when()
                .delete(USERS_PATH_WITH_PARAMS, deletedPersonId)
                .then()
                .statusCode(HTTP_NO_CONTENT)
                .extract()
                .response();

        Allure.step("Validate response");
        assertThat(response.asString()).isEmpty();

    }
}
