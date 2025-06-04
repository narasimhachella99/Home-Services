package com.example.homeservices.service.impl

import com.example.homeservices.model.User
import com.example.homeservices.repository.IUserRepository
import com.example.homeservices.service.IUserService
import org.springframework.stereotype.Service

@Service
class UserService(val  userRepository:IUserRepository):IUserService {

    override fun allUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).get()
    }

    override fun addUser(user: User): User {
        return userRepository.save(user)
    }

    override fun updateUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    override fun getByEmailAndPassword(email: String, password: String): User? {
        return userRepository.findByEmailAndPassword(email, password)
    }

    override fun getByEmail(email: String?): User? {
        return userRepository.findByEmail(email)
    }

    override fun getByName(name: String?): User? {
        return userRepository.findByName(name)
    }
}