package com.example.homeservices.service

import com.example.homeservices.model.GetOrder

interface IGetOrderService {

    fun allOrders():List<GetOrder>
    fun getById(id:Long):GetOrder
    fun addOrder(getOrder: GetOrder):GetOrder
    fun updateOrder(getOrder: GetOrder):GetOrder
    fun deleteOrder(id:Long)
}