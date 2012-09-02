package com.foodible.user

class User {

	String email

    String password

    String firstname
    String lastname
    String registrationCode

    boolean enabled

    boolean accountExpired

    boolean accountLocked

    boolean passwordExpired

	static constraints = {
        email blank: false, unique: true, email: true
		password blank: false
        firstname blank: false
        lastname blank: false
        registrationCode nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
}
