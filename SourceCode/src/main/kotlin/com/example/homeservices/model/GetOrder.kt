package com.example.homeservices.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class GetOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long=0,
    var userName:String="",
    var workerName:String="",
    var userNumber:Long=0,
    var workType:String="",
    var orderValue:Long=0,
    var status:String="Not Accepted"
)
