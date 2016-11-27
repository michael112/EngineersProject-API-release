package main.util.password.generator;

import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

@Service("randomPasswordGenerator")
public class RandomPasswordGeneratorImpl implements RandomPasswordGenerator {

    public String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(0, 13) + 8);
    }

}
