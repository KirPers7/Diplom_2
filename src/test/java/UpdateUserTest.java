import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.users.Credentials;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseTest {

    @Test
    @DisplayName("update user")
    @Description("Обновление данных пользователя")
    public void updateUserTest() {

        Credentials creds = Credentials.from(user);

        accessToken = userClient.logIn(creds).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");

        userClient.updateUser(otherDataUser, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(otherDataUser.getEmail().toLowerCase()))
                .body("user.name", equalTo(otherDataUser.getName()));
    }

    @Test
    @DisplayName("update unauthorized user")
    @Description("Ошибка 401 при попытке обновления данных неавторизованного пользователя")
    public void updateUnauthorizedUserTest() {
        userClient.updateUnauthorizedUser(otherDataUser).log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false));
    }
}
