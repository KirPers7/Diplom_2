package pojo;

import java.util.List;

public class Order {

    private List<String> ingredients;

    public Order(List<String> ingredientItem) {
        this.ingredients = ingredientItem;
    }

    public Order() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
