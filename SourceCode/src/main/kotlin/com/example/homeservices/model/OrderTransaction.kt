package com.example.homeservices.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class OrderTransaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long=0,
    var date:String="",
    var fromUser:String="",
    var toUser:String="",

)
