package com.bavithbhargav.chatservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class MongoDBUtils {

    public static String generateRandomPrimaryID(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
