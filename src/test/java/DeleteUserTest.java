import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserTest extends BaseTest {

    @Test
    void deleteUserTest() {
        String personName = "Ferrus Manus";
        String personJob = "Primarch";
        String deletedPersonId = "10";

        Person person = new Person(personName, personJob);

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
