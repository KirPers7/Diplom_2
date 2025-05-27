package data;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.users.Credentials;
import pojo.users.User;
import pojo.users.UserInfo;

public class UserGenerator {

    public static final String EMAIL_DOMAIN = "@yandex.ru";

    public User random() {
        return new User(RandomStringUtils.randomAlphabetic(6)+EMAIL_DOMAIN,
                RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(6));
    }

    public User failRandom() {
        return new User (RandomStringUtils.randomAlphabetic(6)+EMAIL_DOMAIN,
                null, RandomStringUtils.randomAlphabetic(6));
    }

    public Credentials otherRandomCreds() {
        return new Credentials(RandomStringUtils.randomAlphabetic(6)+EMAIL_DOMAIN,
                RandomStringUtils.randomAlphabetic(6));
    }

    public UserInfo randomUpdateUser() {
        return new UserInfo(RandomStringUtils.randomAlphabetic(6)+EMAIL_DOMAIN,
                RandomStringUtils.randomAlphabetic(6));
    }
}
