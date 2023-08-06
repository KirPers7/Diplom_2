import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest{

    @Test
    @DisplayName("check create user with same data")
    @Description("Ошибка 403, когда такие данные при создании пользователя уже отправлялись")
    public void createUserWithSameDataTest() {
        userClient.createUser(user).log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("check create user without password")
    @Description("Ошибка 403, когда пользователя создают без пароля")
    public void createUserWithoutPasswordTest() {
        userClient.createUser(failUser).log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false));
    }
}
