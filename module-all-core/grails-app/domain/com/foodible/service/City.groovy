package com.foodible.service

class City {

    String name

    Date dateCreated

    Date lastUpdated

    static constraints = {
        name(blank: false, nullable: false, unique: true)
    }
}
