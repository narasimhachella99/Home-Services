package com.example.homeservices.repository

import com.example.homeservices.model.OrderTransaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITransactionRepository:JpaRepository<OrderTransaction,Long> {

}