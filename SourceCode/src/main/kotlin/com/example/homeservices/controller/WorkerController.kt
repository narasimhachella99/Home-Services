package com.example.homeservices.controller

import com.example.homeservices.model.GetOrder
import com.example.homeservices.model.User
import com.example.homeservices.model.Worker
import com.example.homeservices.service.IGetOrderService
import com.example.homeservices.service.IWorkerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WorkerController(val workerService: IWorkerService,
                       val getOrderService: IGetOrderService,
                       val req:HttpServletRequest) {

// Here we  are creating shortcut for pages

    private val WORKER_REGISTER: String="worker/register"
    private val WORKER_LOGIN: String="worker/login"


    //Here we are getting all the orders by the users

    @GetMapping("/orders")
    fun orders(model: Model):String{
        var  worker=req.session.getAttribute("worker") as Worker
        model.addAttribute("orders",getOrderService.allOrders().stream().filter({i->i.workerName==worker.name}).toList())
            return "worker/allOrders"
    }


    //// Here worker is accepting the request by through user

    @PostMapping("/workerApprove")
    fun workApprove(model: Model):String{
        var getOrder=req.session.getAttribute("getOrder") as GetOrder
        getOrder.status="accepted"
        getOrderService.addOrder(getOrder)
        return "redirect:/orders"
    }

    // Here we are getting Order status

    @GetMapping("/orderStatus")
    fun orderStatus(model: Model):String{
        model.addAttribute("orders",getOrderService.allOrders())
        return "worker/allOrders"
    }

    // In this route checking the worker is accept the request or not

    @GetMapping("/checkStatus")
    fun check(model: Model):String{
        var  user=req.session.getAttribute("user") as User
        model.addAttribute("orders",getOrderService.allOrders().stream().filter({i->i.userName==user.name}).toList())
        return "user/allOrders"
    }


    /// Worker can register with own credentials
    /// Here worker have some conditions to get registration
    /// By through Below conditions worker can register

    @PostMapping("/workerRegister")
    private fun workerReg(model: Model, name: String, workType: String, email: String, password: String,phoneNumber:Long): String {
        if (email == "" || password == "" || name == "" || workType == "") {
            model.addAttribute("error", "please fill out all the fields")
        } else {
            val worker: Worker = Worker(name = name, workType=workType, email = email, password = password, phoneNumber = phoneNumber)
            if (workerService.getByEmail(worker.email) != null) {
                model.addAttribute("error", "user already exist");
            } else {
                workerService.addWorker(worker)
                model.addAttribute("success", "registration done successfully")
                req.session.setAttribute("worker", worker)
                return WORKER_LOGIN
            }
        }
        return WORKER_REGISTER
    }


    /// Worker can login with registered credentials
    // By through below conditions

    @PostMapping("/workerLogin")
    private fun workerLogin(model: Model, email: String, password: String): String {
        if (email == "admin@gmail.com" && password == "admin") {
//            val user: User = req.session.getAttribute("user") as User
            model.addAttribute("success", "admin login successfully")
            return "admin/home"
        }
        if (email == "" || password == "") {
            model.addAttribute("error", "please fil out all the fields")
        } else {
            val worker: Worker? = workerService.getByEmailAndPassword(email, password)
            if (worker != null) {
                req.session.setAttribute("worker", worker)
                return "redirect:/workerHome"
            }
        }
        return WORKER_LOGIN;
    }
}