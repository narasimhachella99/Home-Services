package com.example.homeservices.repository

import com.example.homeservices.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository:JpaRepository<User,Long> {

    fun findByEmailAndPassword(email: String, password: String): User?

    fun  findByEmail(email: String?):User?

    fun findByName(name:String?):User?

}

