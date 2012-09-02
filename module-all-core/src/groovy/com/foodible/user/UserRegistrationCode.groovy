package com.foodible.user

import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang.RandomStringUtils

class UserRegistrationCode {

    private final int CODE_LENGTH = 10

    Long userId
    String registrationCode

    public UserRegistrationCode(final User user) {
        this.userId = user.id
        this.registrationCode = generateRegistrationCode()
    }

    public UserRegistrationCode(final String token) {
        String decodedToken = new String(Base64.decodeBase64(token))

        // todo: split and set values
        //def splittedToken = decodedToken.split('.', 1)
    }

    public String toToken() {
        String decodedToken = "${this.userId}.${this.registrationCode}"
        return Base64.encodeBase64URLSafeString(decodedToken.bytes)
    }

    private String generateRegistrationCode() {
        return RandomStringUtils.randomAlphanumeric(CODE_LENGTH)
    }


}
