import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUsersListTest extends BaseTest {
    String emailDomain = "@reqres.in";
    String avatarUrlPattern = "https://reqres.in/img/faces/";
    String avatarFileType = "image.jpg";

    //todo add test description and some tags like "positive/negative"
    @Test
    @DisplayName("GET /users return list of users")
    void getUsersList() {

        UsersList users =
                given()
                        .spec(getDefaultRequestSpecification())
                    .when()
                        .get(USERS_PATH)
//                        .get(USERS_PATH+"?page=2")
//                        .prettyPeek()
                    .then()
                        .contentType(ContentType.JSON)
                        .statusCode(HTTP_OK)
                        .time(Matchers.lessThan(5000L))
                        .extract().as(UsersList.class);


        assertThat(users.page)
                .isEqualTo(1);

        assertThat(users.perPage)
                .isEqualTo(6);

        assertThat(users.totalPages)
                .isEqualTo(2);
        assertThat(users.data).isNotNull();

        assertThat(users.support.url)
                .isEqualTo(UsersData.DEFAULT_USER_SUPPORT_URL);

        assertThat(users.support.text)
                .isEqualTo(UsersData.DEFAULT_USER_TEXT);

        assertThat(users.data)
                .hasSize(6);

        assertThat(users.data)
                .extracting(Data::getId)
                .isNotNull()
                .allSatisfy(id -> {
                    assertThat(id).isNotNull();
                    assertThat(id).isNotNegative();
                    assertThat(id).isGreaterThan(0);
                });


        assertThat(users.data)
                .extracting(Data::getEmail)
                .isNotNull()
                .allSatisfy(email -> {
                    assertThat(email).endsWith(emailDomain);
                });

        assertThat(users.data)
                .extracting(Data::getAvatar)
                .isNotNull()
                .allSatisfy(avatarUrl -> {
                    assertThat(avatarUrl).startsWith(avatarUrlPattern);
                    assertThat(avatarUrl).endsWith(avatarFileType);
                });

        assertThat(users.data)
                .extracting(Data::getFirstName)
                .isNotNull()
                .allSatisfy(firstName -> {
                    assertThat(firstName).isNotEmpty();
                });

        assertThat(users.data)
                .extracting(Data::getLastName)
                .isNotNull()
                .allSatisfy(lastName -> {
                    assertThat(lastName).isNotEmpty();
                });
    }
}
