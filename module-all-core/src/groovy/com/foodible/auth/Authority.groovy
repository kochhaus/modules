package com.foodible.auth

class Authority {

    public static final String ADMIN = 'ROLE_ADMIN'

    public static final String COOK = 'ROLE_COOK'

    public static final String USER = 'ROLE_USER'

    public static final Set<String> ALLOWED_AUTHORITIES = [ADMIN, COOK, USER]

    public static final Set<String> ALLOWED_USER_AUTHORITIES = [COOK, USER]
}
