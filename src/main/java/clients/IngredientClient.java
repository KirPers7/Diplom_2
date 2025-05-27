package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class IngredientClient extends Client{

    @Step("Запрос на получение части списка доступных ингредиентов")
    public ValidatableResponse getIngredients() {
        return spec()
                .when()
                .get("/ingredients")
                .then().log().all();
    }
}
