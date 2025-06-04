package com.example.homeservices.service.impl

import com.example.homeservices.model.Worker
import com.example.homeservices.repository.IWorkerRepository
import com.example.homeservices.service.IWorkerService
import org.springframework.stereotype.Service

@Service
class WorkerService(val  workerRepository: IWorkerRepository):IWorkerService {
    override fun allWorkers(): List<Worker> {
        return workerRepository.findAll()
    }

    override fun getById(id: Long): Worker {
        return workerRepository.findById(id).get()
    }

    override fun addWorker(worker: Worker): Worker {
        return workerRepository.save(worker)
    }

    override fun updateWorker(worker: Worker): Worker {
        return workerRepository.save(worker)
    }

    override fun deleteWorker(id: Long) {
        workerRepository.deleteById(id)
    }

    override fun getByEmail(email: String): Worker? {
        return workerRepository.findByEmail(email)
    }

    override fun getByEmailAndPassword(email: String, password: String): Worker? {
        return workerRepository.findByEmailAndPassword(email, password)
    }

    override fun getByWorkType(workType: String): List<Worker>? {
        return workerRepository.findByWorkType(workType)
    }
}