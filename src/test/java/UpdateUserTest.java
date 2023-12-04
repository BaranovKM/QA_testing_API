import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.util.ResultsUtils.PARENT_SUITE_LABEL_NAME;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

//todo move base test to src
public class UpdateUserTest extends BaseTest {
    @Test
    @DisplayName("PUT for single user")
    @Description("Make PUT request for single user and validate json in response")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(UPDATE_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void updateUserTest() {
        String personName = "Rogal Dorn";
        String personJob = "Praetorian of Terra";
        String updatedPersonId = "7";
        String updatedAt = "updatedAt";
        LocalDateTime userUpdatedAt;

        Person person = new Person(personName, personJob);

        Allure.label(PARENT_SUITE_LABEL_NAME, UPDATE_USER);
        Allure.step("Make PUT request for user");
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

        Allure.addAttachment(
                "response",
                "application/json",
                response.asPrettyString(),
                ".json");

        Allure.step("Check response contain only timestamp ");
        assertThat(response.as(Map.class).keySet())
                .isNotNull()
                .hasSize(1)
                .containsOnly(updatedAt);

        userUpdatedAt = LocalDateTime.parse(response.jsonPath().getString(updatedAt), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        Allure.step("Check timestamp");
        assertThat(userUpdatedAt).isAfter(LocalDateTime.now().minusHours(4));//server time has huge delay

        /*
            In real life I would add a step to check if the user's date is actually updated, but this is mock api
            and user's data is hardcode and can't be updated
         */

    }
}
