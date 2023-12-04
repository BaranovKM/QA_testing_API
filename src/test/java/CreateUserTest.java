import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static allure.AllureMarks.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.util.ResultsUtils.PARENT_SUITE_LABEL_NAME;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest extends BaseTest {
    @Test
    @DisplayName("POST for single user")
    @Description("Make POST request for single user and validate json in response")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(CREATE_SINGLE_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
    void createUserTest() {
        String personName = "Horus Lupercal";
        String personJob = "Warmaster";
        String id = "id";
        String createdAt = "createdAt";

        Person person = new Person(personName, personJob);

        Allure.label(PARENT_SUITE_LABEL_NAME, CREATE_SINGLE_USER);
        Allure.step("Make POST request");
        RequestSpecification request =
                given()
                    .spec(getDefaultRequestSpecification())
                    .body(person);

        Response response = request
                .when()
                    .post(USERS_PATH)
                .then()
                    .statusCode(HTTP_CREATED)
                    .contentType(ContentType.JSON)
                    .time(Matchers.lessThan(5000L))
                    .extract().response();

        Allure.addAttachment(
                "request_body",
                "application/json",
                SpecificationQuerier.query(request).getBody().toString(),
                ".json");

        Allure.addAttachment(
                "response_body",
                "application/json",
                response.asPrettyString(),
                ".json");

        Allure.step("Validate response", stepContext -> {
            Allure.step("Check response contain only user id and timestamp");
            assertThat(response.as(Map.class).keySet())
                    .isNotNull()
                    .hasSize(2)
                    .containsOnly(id, createdAt);

            Allure.step("Check user id");
            assertThat(response.jsonPath().getInt(id))
                    .isNotNull()
                    .isNotNegative()
                    .isGreaterThan(0);

            LocalDateTime userCreatedAt = LocalDateTime.parse(
                    response.jsonPath().getString(createdAt),
                    DateTimeFormatter.ISO_ZONED_DATE_TIME);

            Allure.step("Check timestamp");
            assertThat(userCreatedAt).isAfter(LocalDateTime.now().minusHours(4));//server time has huge delay
        });
    }
}
