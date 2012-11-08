package com.foodible.user

import com.foodible.service.City
import com.foodible.service.CustomersLimitation
import com.foodible.service.HourlyRate

class Cook extends User {

    String name

    HourlyRate hourlyRate

    CustomersLimitation customersLimitation

    static hasMany = [cities:City]

    static constraints = {
        name(nullable: false, blank: false)
        hourlyRate(nullable: false)
        customersLimitation(nullable: false)
        cities(minSize: 1, maxSize: 3)
    }
}
