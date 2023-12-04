import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static allure.AllureMarks.*;
import static allure.AllureMarks.TEST_123;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUsersListTest extends BaseTest {
    String emailDomain = "@reqres.in";
    String avatarUrlPattern = "https://reqres.in/img/faces/";
    String avatarFileType = "image.jpg";

    @Test
    @DisplayName("GET for list of users")
    @Description("Make GET request for list of users and validate json in response")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(GET_USERS_LIST)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    void getUsersList() {

        Allure.step("Make GET request for list of users");
        //todo make refactoring and separate request and response for attachment to allure report
        Allure.step("Make GET request for single user");
        ValidatableResponse response =
                given()
                        .spec(getDefaultRequestSpecification())
                        .when()
                        .get(USERS_PATH)
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

        UsersList users = response.extract().as(UsersList.class);

        Allure.step("Validate response", stepContext -> {

            Allure.step("Check pages quantity");
            assertThat(users.page)
                    .isEqualTo(1);

            Allure.step("Check users quantity per page");
            assertThat(users.perPage)
                    .isEqualTo(6);

            Allure.step("Check total pages quantity");
            assertThat(users.totalPages)
                    .isEqualTo(2);

            Allure.step("Check support url");
            assertThat(users.support.url)
                    .isEqualTo(UsersData.DEFAULT_USER_SUPPORT_URL);

            Allure.step("Check support test");
            assertThat(users.support.text)
                    .isEqualTo(UsersData.DEFAULT_USER_TEXT);

            Allure.step("Check users data quantity");
            assertThat(users.data)
                    .hasSize(6);

            Allure.step("Check users ids");
            assertThat(users.data)
                    .extracting(Data::getId)
                    .isNotNull()
                    .allSatisfy(id -> {
                        assertThat(id).isNotNull();
                        assertThat(id).isNotNegative();
                        assertThat(id).isGreaterThan(0);
                    });

            Allure.step("Check users emails");
            assertThat(users.data)
                    .extracting(Data::getEmail)
                    .isNotNull()
                    .allSatisfy(email -> {
                        assertThat(email).endsWith(emailDomain);
                    });

            Allure.step("Check users avatars");
            assertThat(users.data)
                    .extracting(Data::getAvatar)
                    .isNotNull()
                    .allSatisfy(avatarUrl -> {
                        assertThat(avatarUrl).startsWith(avatarUrlPattern);
                        assertThat(avatarUrl).endsWith(avatarFileType);
                    });

            Allure.step("Check users first names are not empty");
            assertThat(users.data)
                    .extracting(Data::getFirstName)
                    .isNotNull()
                    .allSatisfy(firstName -> {
                        assertThat(firstName).isNotEmpty();
                    });

            Allure.step("Check users last names are not empty");
            assertThat(users.data)
                    .extracting(Data::getLastName)
                    .isNotNull()
                    .allSatisfy(lastName -> {
                        assertThat(lastName).isNotEmpty();
                    });
        });
    }
}
