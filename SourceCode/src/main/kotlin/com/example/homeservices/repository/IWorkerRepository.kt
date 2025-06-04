package com.example.homeservices.repository

import com.example.homeservices.model.User
import com.example.homeservices.model.Worker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IWorkerRepository :JpaRepository<Worker,Long>{

    fun  findByEmail(email:String):Worker?

    fun findByEmailAndPassword(email: String,password:String):Worker?

    fun findByWorkType(workType:String):List<Worker>?

}