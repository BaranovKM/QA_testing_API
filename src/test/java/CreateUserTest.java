import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest extends BaseTest {
    @Test
    @DisplayName("POST for single user")
    @Description("Make POST request for single user and validate json in response")
    @Severity(SeverityLevel.CRITICAL)
    //todo move values to separeted class
    @Owner("Baranov K.M.")
    @Epic("Main system's api")
    @Feature("User creation")
    @Story("Endpoint for creating a single user")
    @Link("http://jira.com/ticket-12345")
    @TmsLink("http://testrail.com/test-12345")
    void createUserTest() {
        String personName = "Horus Lupercal";
        String personJob = "Warmaster";
        String id = "id";
        String createdAt = "createdAt";

        Person person = new Person(personName, personJob);

        Allure.step("Make POST request");

        RequestSpecification request = given()
                .spec(getDefaultRequestSpecification())
                .body(person);

        Response response = request
                .when()
                .post(USERS_PATH)
                .then()
                .statusCode(HTTP_CREATED)
                .contentType(ContentType.JSON)
                .time(Matchers.lessThan(5000L))
                .extract()
                .response();

        Allure.addAttachment(
                "request",
                "application/json",
                SpecificationQuerier.query(request).getBody().toString(),
                ".json");

        Allure.addAttachment(
                "response",
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
