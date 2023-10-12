import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest extends BaseTest {
    @DisplayName("POST for single user")
    @Description("Make POST request for single user and validate json in response")
    @Link("http://jira.com/test-12345")
    @Feature("API tests")
    @Owner("Baranov K.M.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void createUserTest() {
        String personName = "Horus Lupercal";
        String personJob = "Warmaster";
        String id = "id";
        String createdAt = "createdAt";
        LocalDateTime userCreatedAt;

        Person person = new Person(personName, personJob);

        Response response =
                given()
                        .spec(getDefaultRequestSpecification())
                        .body(person)
                    .when()
                        .post(USERS_PATH)
                    .then()
                        .statusCode(HTTP_CREATED)
                        .contentType(ContentType.JSON)
                        .time(Matchers.lessThan(5000L))
                        .extract()
                        .response();

        assertThat(response.as(Map.class).keySet())
                .isNotNull()
                .hasSize(2)
                .containsOnly(id, createdAt);

        assertThat(response.jsonPath().getInt(id))
                .isNotNull()
                .isNotNegative()
                .isGreaterThan(0);

        userCreatedAt = LocalDateTime.parse(response.jsonPath().getString(createdAt), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        assertThat(userCreatedAt).isAfter(LocalDateTime.now().minusHours(4));//server time has huge delay

    }
}
