package com.example.homeservices.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var phoneNumber: Long = 0,
    var isApproved: Boolean=false,
    var status:String="Not Approved"
)
