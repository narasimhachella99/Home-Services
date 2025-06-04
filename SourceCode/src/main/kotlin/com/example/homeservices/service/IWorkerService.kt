package com.example.homeservices.service

import com.example.homeservices.model.User
import com.example.homeservices.model.Worker
import org.hibernate.jdbc.Work

interface IWorkerService {

    fun allWorkers():List<Worker>
    fun getById(id:Long):Worker
    fun addWorker(worker: Worker):Worker
    fun updateWorker(worker: Worker):Worker
    fun deleteWorker(id: Long)
    fun getByEmail(email:String):Worker?
    fun getByEmailAndPassword(email: String,password:String):Worker?

    fun getByWorkType(workType:String):List<Worker>?

}