import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.users.Credentials;

import java.net.HttpURLConnection;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest{

    @Test
    @DisplayName("authorization")
    @Description("Регистрация пользователя")
    public void logInTest() {

        Credentials creds = Credentials.from(user);

        accessToken = userClient.logIn(creds).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("get orders for unauthorized user")
    @Description("Ошибка 401 при попытке авторизации незарегистрированного пользователя")
    public void LogInNotCreatedUserTest() {

        userClient.logIn(otherUser).log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false));
        ;
    }
}
