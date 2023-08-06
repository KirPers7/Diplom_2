package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.users.Credentials;
import pojo.users.User;
import pojo.users.UserInfo;


public class UserClient extends Client {

    @Step("Запрос на создание пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post("/auth/register") //CREATE USER
                .then().log().all();
    }

    @Step("Запрос на авторизацию существующего пользователя")
    public ValidatableResponse logIn(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post("/auth/login") //LOG IN
                .then().log().all();
    }

    @Step("Запрос на обновление информации авторизованного пользователя")
    public ValidatableResponse updateUser(UserInfo userInfo, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(userInfo)
                .when()
                .patch("/auth/user")
                .then().log().all();
    }

    @Step("Запрос на обновление информации неавторизованного пользователя")
    public ValidatableResponse updateUnauthorizedUser(UserInfo userInfo) {
        return spec()
                .body(userInfo)
                .when()
                .patch("/auth/user")
                .then().log().all();
    }

    @Step("Запрос на удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete("/auth/user") //DELETE
                .then().log().all();
    }

}
