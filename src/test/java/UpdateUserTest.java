import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserTest extends BaseTest {
    @DisplayName("PUT for single user")
    @Description("Make PUT request for single user and validate json in response")
    @Link("http://jira.com/test-12345")
    @Feature("API tests")
    @Owner("Baranov K.M.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void updateUserTest() {
        String personName = "Rogal Dorn";
        String personJob = "Praetorian of Terra";
        String updatedPersonId = "7";
        String updatedAt = "updatedAt";
        LocalDateTime userUpdatedAt;

        Person person = new Person(personName, personJob);

        Response response =
                given()
                    .spec(getDefaultRequestSpecification())
                    .body(person)
                .when()
                    .put(USERS_PATH_WITH_PARAMS, updatedPersonId)
                .then()
                    .statusCode(HTTP_OK)
                    .contentType(ContentType.JSON)
                    .time(Matchers.lessThan(5000L))
                    .extract()
                    .response();

        assertThat(response.as(Map.class).keySet())
                .isNotNull()
                .hasSize(1)
                .containsOnly(updatedAt);

        userUpdatedAt = LocalDateTime.parse(response.jsonPath().getString(updatedAt), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        assertThat(userUpdatedAt).isAfter(LocalDateTime.now().minusHours(4));//server time has huge delay

    }
}
