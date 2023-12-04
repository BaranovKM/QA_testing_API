import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.*;

import io.qameta.allure.*;
//import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetSingleUserTest extends BaseTest {
    @Test
    @DisplayName("GET for single user")
    @Description("Make GET request for single user and validate json in response")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(GET_SINGLE_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void getSingleUser() {

        //todo make refactoring and separate request and response for attachment to allure report
        Allure.step("Make GET request for single user");
        ValidatableResponse response =                 given()
                .spec(getDefaultRequestSpecification())
                .when()
                .get(USERS_PATH_WITH_PARAMS, UsersData.DEFAULT_USER_ID)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HTTP_OK)
                .time(Matchers.lessThan(5000L));

        //todo add request in allure report
        Allure.addAttachment(
                "response",
                "application/json",
                response.extract().asPrettyString(),
                ".json");

        User user = response.extract().as(User.class);

        Allure.step("Validate response", stepContext -> {
            Allure.step("Check user's id");
            assertThat(user.data.id)
                    .isEqualTo(UsersData.DEFAULT_USER_ID);

            Allure.step("Check user's email");
            assertThat(user.data.email)
                    .isEqualTo(UsersData.DEFAULT_USER_EMAIL);

            Allure.step("Check user's avatar");
            assertThat(user.data.avatar)
                    .isEqualTo(UsersData.DEFAULT_USER_AVATAR_URL);

            Allure.step("Check user's first name");
            assertThat(user.data.getFirstName())
                    .isEqualTo(UsersData.DEFAULT_USER_FIRST_NAME);

            Allure.step("Check user's last name");
            assertThat(user.data.getLastName())
                    .isEqualTo(UsersData.DEFAULT_USER_LAST_NAME);

            Allure.step("Check support link");
            assertThat(user.support.url)
                    .isEqualTo(UsersData.DEFAULT_USER_SUPPORT_URL);

            Allure.step("Check support text");
            assertThat(user.support.text)
                    .isEqualTo(UsersData.DEFAULT_USER_TEXT);
        });
    }
}
