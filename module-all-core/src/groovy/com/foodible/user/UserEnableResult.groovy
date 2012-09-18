package com.foodible.user

class UserEnableResult {

    final static int INTERNAL_ERROR = 0
    final static int SUCCESS = 1
    final static int ALREADY_REGISTERED = 2
    final static int USER_NOT_FOUND = 3
    final static int TOKEN_INCORRECT = 4

    String token
    User user
    Integer status

}
