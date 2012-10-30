package com.foodible

class City {

    String name

    static constraints = {
        name(blank: false, nullable: false, unique: true)
    }
}
