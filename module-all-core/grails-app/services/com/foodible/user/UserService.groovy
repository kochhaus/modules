package com.foodible.user

import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException

class UserService {

    def springSecurityService
    def doiMailService

    @Transactional
    public User createUser(final String email
                           , final String password
                           , final String authority
                           , final String firstname
                           , final String lastname
                           , final boolean enabled) {
        final Role role = Role.findByAuthority(authority)

        if (!role){
            throw new IllegalArgumentException("Authority ${authority} not exits.")
        }

        final String encodedPassword = springSecurityService.encodePassword(password)
        final User user = new User(email: email
                , password: encodedPassword
                , firstname: firstname
                , lastname: lastname
                , enabled: enabled)
        user.save()

        if (user.hasErrors()){
            return null
        }

        final UserRole userRole = UserRole.create(user, role, true)
        return userRole.user
    }

    @Transactional
    public void sendDOIMail(User user) {

        // do not send DOI mail if user is enabled
        if(user.isEnabled()) {
            return
        }

        UserRegistrationCode userRegistrationCode = new UserRegistrationCode(user)
        user.registrationCode = userRegistrationCode.registrationCode
        user.save()

        String token = userRegistrationCode.toToken()

        // todo: findout how to get URL from portal-app
        String doiURL = "http://localhost:8080/portal/enable/$token"
        doiMailService.sendDOIMail(doiURL)
    }

    @Transactional
    public def enableAccount(String token) {

        UserEnableResult bean = new UserEnableResult(token)
        try {

            UserRegistrationCode userRegistrationCode = new UserRegistrationCode(bean.token)
            User user = User.get(userRegistrationCode.userId)

            if(!user){
                bean.status = UserEnableResult.USER_NOT_FOUND
            } else if(user.isEnabled()) {
                bean.status = UserEnableResult.ALREADY_REGISTERED
            } else if(user.registrationCode.equals(userRegistrationCode.registrationCode)) {
                bean.status = UserEnableResult.TOKEN_INCORRECT
            } else {
                user.setEnabled(true)
                user.save()
                bean.status = UserEnableResult.SUCCESS
            }
        } catch (Exception e) {
            bean.status = UserEnableResult.INTERNAL_ERROR
        }
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
