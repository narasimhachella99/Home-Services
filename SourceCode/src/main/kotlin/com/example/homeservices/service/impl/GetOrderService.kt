package com.example.homeservices.service.impl

import com.example.homeservices.model.GetOrder
import com.example.homeservices.repository.IGetOrderRepository
import com.example.homeservices.service.IGetOrderService
import org.springframework.stereotype.Service

@Service
 class GetOrderService  ( var getOrderRepository:IGetOrderRepository):IGetOrderService{
    override fun allOrders(): List<GetOrder> {
        return getOrderRepository.findAll()
    }

    override fun getById(id: Long): GetOrder {
       return getOrderRepository.findById(id).get()
    }

    override fun addOrder(getOrder: GetOrder): GetOrder {
        return getOrderRepository.save(getOrder)
    }

    override fun updateOrder(getOrder: GetOrder): GetOrder {
        return getOrderRepository.save(getOrder)
    }

    override fun deleteOrder(id: Long) {
        return getOrderRepository.deleteById(id)
    }
}