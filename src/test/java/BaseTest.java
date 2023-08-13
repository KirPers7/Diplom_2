import clients.IngredientClient;
import clients.OrderClient;
import clients.UserClient;
import data.OrderGenerator;
import data.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import pojo.Order;
import pojo.users.Credentials;
import pojo.users.User;
import pojo.users.UserInfo;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {

    protected final UserGenerator generator = new UserGenerator();
    protected final UserClient userClient = new UserClient();
    protected final OrderClient orderClient = new OrderClient();
    protected final OrderGenerator IncorrectHashGenerator = new OrderGenerator();
    protected final IngredientClient ingredients = new IngredientClient();
    protected String accessToken;
    protected String refreshToken;
    protected String name;
    protected User user = generator.random();
    protected User failUser = generator.failRandom();
    protected Credentials otherUser = generator.otherRandomCreds();
    protected UserInfo otherDataUser = generator.randomUpdateUser();
    protected Order orderWithIncorrectHash = IncorrectHashGenerator.randomIngredient();

    @Before
    @DisplayName("check create user")
    @Description("Создаётся успешно пользователь")
    public void createUser() {

        ExtractableResponse<Response> extract = userClient.createUser(user).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract();
        accessToken = extract.path("accessToken");
        refreshToken = extract.path("refreshToken");
    }

    @After
    @DisplayName("check delete user")
    @Description("Удаляется успешно пользователь")
    public void deleteUserTest() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken).log().all();
        }
    }
}
