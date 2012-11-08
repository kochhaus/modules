package com.foodible.service

class HourlyRate {

    Float value

    Date dateCreated

    Date lastUpdated

    static constraints = {
        value(nullable: false, min: 0F)
    }
}
