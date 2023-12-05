import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.util.ResultsUtils.PARENT_SUITE_LABEL_NAME;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
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
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
    @Issue(BUG_123)
    void deleteUserTest() {
        String deletedPersonId = "10";

        Allure.label(PARENT_SUITE_LABEL_NAME, REMOVE_USER);
        Allure.step("Make DELETE request");
        Response response =
                given()
                        .spec(getDefaultRequestSpecification())
                        .when()
                        .delete(USERS_PATH_WITH_PARAMS, deletedPersonId);

        Allure.step("Check status code 204");
        response.then().statusCode(HTTP_NO_CONTENT);

        Allure.step("Check response body is empty");
        assertThat(response.asString()).isEmpty();

        Allure.step("Try to get removed user");
        Response repeatedResponse = given()
                .spec(getDefaultRequestSpecification())
                .when()
                .get(USERS_PATH_WITH_PARAMS, deletedPersonId);

        Allure.step("Check status code 404", stepContext -> {
            repeatedResponse.then()
                    .statusCode(HTTP_NOT_FOUND);
        });

    }
}
