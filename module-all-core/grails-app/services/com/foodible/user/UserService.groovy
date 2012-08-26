package com.foodible.user

import org.springframework.transaction.annotation.Transactional

class UserService {

    def springSecurityService

    @Transactional
    public User createUser(final String email, final String password, final String authority) {
        final Role role = Role.findByAuthority(authority)

        if (!role){
            throw new IllegalArgumentException("Authority ${authority} not exits.")
        }

        final String encodedPassword = springSecurityService.encodePassword(password)
        final User user = new User(email: email, password: encodedPassword, enabled: true).save()

        if (user.hasErrors()){
            return user
        }

        final UserRole userRole = UserRole.create(user, role, true)
        return userRole.user
    }
}
