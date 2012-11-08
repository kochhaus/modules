package com.foodible.service

class KitchenType {

    String name

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
