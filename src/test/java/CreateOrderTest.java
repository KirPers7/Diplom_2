import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.Order;
import pojo.users.Credentials;

import java.util.List;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseTest{

    @Test
    @DisplayName("check create order for authorized user")
    @Description("Создаётся успешно заказ для авторизованного пользователя")
    public void createOrderTest() {

        List<String> list = ingredients.getIngredients().log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body(notNullValue())
                .extract()
                .body().jsonPath().getList("data[0..3]._id")
                .stream().map(e -> e.toString()).collect(Collectors.toList());

        Order order = new Order(list);

        Credentials credentials = Credentials.from(user);

        userClient.logIn(credentials).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue());

        orderClient.createOrder(order, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("check create order without authorization")
    @Description("Создаётся успешно заказ без авторизации")
    public void createOrderWithoutAuthorizationTest() {

        List<String> list = ingredients.getIngredients().log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body(notNullValue())
                .extract()
                .body().jsonPath().getList("data[0..3]._id")
                .stream().map(e -> e.toString()).collect(Collectors.toList());

        Order order = new Order(list);

        orderClient.createOrderWithoutAuthorization(order).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("check create order without ingredients - 400 bad request")
    @Description("Проверка что вышла ошибка 400, когда заказ без ингредиентов")
    public void createOrderWithoutIngredients() {

        Order order = new Order();

        Credentials credentials = Credentials.from(user);

        userClient.logIn(credentials).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue());

        orderClient.createOrder(order, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("check create order with incorrect hash - 500 internal error")
    @Description("Ошибка 500 при создании заказа с некорректным хэшем ингредиента")
    public void createOrderWithIncorrectHash() {

        Credentials credentials = Credentials.from(user);

        userClient.logIn(credentials).log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue());

        orderClient.createOrder(orderWithIncorrectHash, accessToken).log().all()
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}
