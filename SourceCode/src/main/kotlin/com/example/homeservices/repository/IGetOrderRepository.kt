package com.example.homeservices.repository

import com.example.homeservices.model.GetOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IGetOrderRepository :JpaRepository<GetOrder,Long>{
}