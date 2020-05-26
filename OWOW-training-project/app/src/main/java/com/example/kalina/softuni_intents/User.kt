package com.example.kalina.softuni_intents

class User {
    var id: Int = 0
    lateinit var firstName: String
    lateinit var lastName: String
    constructor()

    constructor(id: Int, firstName: String, lastName:String) {
        this.id= id
        this.firstName = firstName
        this.lastName = lastName
    }
}
