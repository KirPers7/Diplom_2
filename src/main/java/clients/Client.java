package clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Client {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String USER_API = "/api";
    @Step("Переход на стенд по ссылке")
    protected RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(USER_API);
    }
}
