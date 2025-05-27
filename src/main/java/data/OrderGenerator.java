package data;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.Order;

import java.util.Collections;

public class OrderGenerator {

    public Order randomIngredient() {
        return new Order(Collections.singletonList(RandomStringUtils.randomAlphabetic(24)));
    }
}
