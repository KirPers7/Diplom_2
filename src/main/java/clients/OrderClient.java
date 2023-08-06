package clients;

import io.qameta.allure.Step;
import pojo.Order;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {

    @Step("Запрос на создание заказа авторизованным пользователем")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Запрос на создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return spec()
                .body(order)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Запрос на получение всех заказов авторизованного пользователя")
    public ValidatableResponse getUsersOrder(String name, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get("/orders")
                .then().log().all();
    }

    @Step("Запрос на получение всех заказов неавторизованного пользователя")
    public ValidatableResponse getUsersOrderWithoutAuthorization(String name) {
        return spec()
                .when()
                .get("/orders")
                .then().log().all();
    }

}
