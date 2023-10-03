import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class TestSingleUserNotFound extends BaseTest {

    int wrongUserId = 50;

    @Test
    void singleUserNotFound() {
        given()
            .spec(getDefaultRequestSpecification())
        .when()
            .get(USERS_PATH_WITH_PARAMS, wrongUserId)
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HTTP_NOT_FOUND)
            .body(Matchers.is(EMPTY_JSON));
    }
}
