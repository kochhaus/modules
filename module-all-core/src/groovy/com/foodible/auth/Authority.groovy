package com.foodible.auth

class Authority {

    public static final String ADMIN = 'ADMIN'

    public static final String COOK = 'COOK'

    public static final String USER = 'USER'

    public static final Set<String> ALLOWED_AUTHORITIES = [ADMIN, COOK, USER]
}
