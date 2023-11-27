import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.*;

import io.qameta.allure.*;
//import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetSingleUserTest extends BaseTest {
    @DisplayName("GET for single user")
    @Description("Make GET request for single user and validate json in response")
    @Link("http://jira.com/test-12345")
    @Feature("API tests")
    @Owner("Baranov K.M.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void getSingleUser() {

        User user =
            given()
                .spec(getDefaultRequestSpecification())
                    //commented until fix AllureRestAssured dependencies
//                .filter(new AllureRestAssured())
            .when()
                .get(USERS_PATH_WITH_PARAMS, UsersData.DEFAULT_USER_ID)
            .then()
                .contentType(ContentType.JSON)
                .statusCode(HTTP_OK)
                .time(Matchers.lessThan(5000L))
                .extract().as(User.class);

        assertThat(user.data.id)
                .isEqualTo(UsersData.DEFAULT_USER_ID);

        assertThat(user.data.email)
                .isEqualTo(UsersData.DEFAULT_USER_EMAIL);

        assertThat(user.data.avatar)
                .isEqualTo(UsersData.DEFAULT_USER_AVATAR_URL);

        assertThat(user.data.getFirstName())
                .isEqualTo(UsersData.DEFAULT_USER_FIRST_NAME);

        assertThat(user.data.getLastName())
                .isEqualTo(UsersData.DEFAULT_USER_LAST_NAME);

        assertThat(user.support.url)
                .isEqualTo(UsersData.DEFAULT_USER_SUPPORT_URL);

        assertThat(user.support.text)
                .isEqualTo(UsersData.DEFAULT_USER_TEXT);
    }
}
