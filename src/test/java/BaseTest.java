import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseTest {
    public static final String BASE_URI = "https://reqres.in/api";

    public static final String USERS_PATH = "/users";
    public static final String USERS_PATH_WITH_PARAMS = "/users/{id}";

    public static final String EMPTY_JSON = "{}";

    public static RequestSpecification getDefaultRequestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .build()
                .log()
                .all();
    }

}
