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

        doiMailService.sendDOIMail('www.irgendwas.de/enable', token)
    }

    @Transactional
    public def enableAccount(UserEnableBean bean) {

        try {

            UserRegistrationCode userRegistrationCode = new UserRegistrationCode(bean.token)
            User user = User.get(userRegistrationCode.userId)

            if(!user){
                bean.status = UserEnableBean.USER_NOT_FOUND
            } else if(user.isEnabled()) {
                bean.status = UserEnableBean.ALREADY_REGISTERED
            } else if(user.registrationCode.equals(userRegistrationCode.registrationCode)) {
                bean.status = UserEnableBean.TOKEN_INCORRECT
            } else {
                user.setEnabled(true)
                user.save()
                bean.status = UserEnableBean.SUCCESS
            }
        } catch (Exception e) {
            bean.status = UserEnableBean.INTERNAL_ERROR
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
