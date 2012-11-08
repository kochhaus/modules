package com.foodible.service

class CustomersLimitation {

    Integer bottom

    Integer top

    Date dateCreated

    Date lastUpdated

    static constraints = {
        bottom(nullable: false, min: 1)
        top(nullable: false, min: 1, validator: {val, obj -> val > obj.bottom})
    }

    @Override
    String toString() {
        return "${bottom}-${top}"
    }
}
