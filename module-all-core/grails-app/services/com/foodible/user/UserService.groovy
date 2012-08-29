package com.foodible.user

import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException

class UserService {

    def springSecurityService

    @Transactional
    public User createUser(final String email, final String password, final String authority, final boolean enabled) {
        final Role role = Role.findByAuthority(authority)

        if (!role){
            throw new IllegalArgumentException("Authority ${authority} not exits.")
        }

        final String encodedPassword = springSecurityService.encodePassword(password)
        final User user = new User(email: email, password: encodedPassword, enabled: enabled).save()

        if (user.hasErrors()){
            return null
        }

        final UserRole userRole = UserRole.create(user, role, true)
        return userRole.user
    }

    @Transactional
    public boolean delete(final Long id) {
        final User userInstance = User.get(id)

        if (!userInstance) {
            throw new EntityNotFoundException("User with id ${id} not found")
        }

        UserRole.removeAll(userInstance)
        userInstance.delete()
        return true
    }
}
