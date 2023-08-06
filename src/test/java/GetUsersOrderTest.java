import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.Order;
import pojo.users.Credentials;

import java.util.List;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetUsersOrderTest extends BaseTest{



    @Test
    @DisplayName("get orders for authorized user")
    @Description("Получение всех заказов авторизованного пользователя")
    public void getOrderTest() {

        List<String> list = ingredients.getIngredients().log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body(notNullValue())
                .extract()
                .body().jsonPath().getList("data[0..3]._id")
                .stream().map(e -> e.toString()).collect(Collectors.toList());

        Order order = new Order(list);

        Credentials credentials = Credentials.from(user);

        name = userClient.logIn(credentials).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue())
                .extract()
                .path("user.name");

        orderClient.createOrder(order, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));

        orderClient.getUsersOrder(name, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("get orders for unauthorized user")
    @Description("Ошибка 401 при попытке получения заказов неавторизованного пользователя")
    public void getOrderWithoutAuthorizationTest() {

        List<String> list = ingredients.getIngredients().log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body(notNullValue())
                .extract()
                .body().jsonPath().getList("data[0..3]._id")
                .stream().map(e -> e.toString()).collect(Collectors.toList());

        Order order = new Order(list);

        Credentials credentials = Credentials.from(user);

        name = userClient.logIn(credentials).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue())
                .extract()
                .path("user.name");

        orderClient.createOrder(order, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));

        orderClient.getUsersOrderWithoutAuthorization(name).log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false));
    }
}
