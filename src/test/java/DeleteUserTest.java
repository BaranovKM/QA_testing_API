import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserTest extends BaseTest {

    @DisplayName("DELETE for single user")
    @Description("Make DELETE request for single user and check json in response is empty")
    @Link("http://jira.com/test-12345")
    @Feature("API tests")
    @Owner("Baranov K.M.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void deleteUserTest() {
        String deletedPersonId = "10";

        Response response =
                given()
                        .spec(getDefaultRequestSpecification())
                    .when()
                        .delete(USERS_PATH_WITH_PARAMS, deletedPersonId)
                    .then()
                        .statusCode(HTTP_NO_CONTENT)
                        .extract()
                        .response();

        assertThat(response.asString()).isEmpty();

    }
}
