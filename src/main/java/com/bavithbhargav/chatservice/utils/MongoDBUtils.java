package com.bavithbhargav.chatservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class MongoDBUtils {

    public static String generateUserPrimaryID(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
