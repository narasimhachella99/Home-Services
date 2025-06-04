package com.example.homeservices.controller

import jakarta.servlet.http.HttpServletRequest
import com.example.homeservices.model.User
import com.example.homeservices.model.Worker
import com.example.homeservices.service.IUserService
import com.example.homeservices.service.IWorkerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    val req: HttpServletRequest,
    val workerService: IWorkerService,
    val userService: IUserService
) {


    // Here we  are creating shortcut for pages

    private val WORKER_LOG: String = "worker/login"
    private val WORKER_HOME: String = "worker/home"
    private val WORKER_REG: String = "worker/register"
    private val USER_REG: String = "user/register"
    private val USER_HOME: String = "user/home"


    /// It returns index page

    @GetMapping("/")
    fun Home(): String {
        return "index";
    }

    // It returns user login page

    @GetMapping("/userLogin")
    fun userLog(): String {
        return "user/login";
    }

    // It returns transaction page

    @GetMapping("/transaction")
    fun transaction():String{
        return "user/transaction"
    }

    // Here user can get the worker booking page

    @GetMapping("/bookOrder")
    fun bookOrder(model: Model): String {
        var worker: Worker = req.session.getAttribute("worker") as Worker
        var user:User=req.session.getAttribute("user") as  User
        model.addAttribute("workers", workerService.allWorkers())
        model.addAttribute("users",userService.getById(user.id))
        return "user/orderPage";
    }

    /// By this route user can get selected workers

    @GetMapping("/bookOrder1")
    fun order(model:Model,workType:String):String{
        model.addAttribute("workers",workerService.getByWorkType(workType))
        return "user/allWorkers"
    }


    /// admin getting all workers

    @GetMapping("/allWorkers")
    fun allWorkers(model: Model): String {
        var worker: Worker = req.session.getAttribute("worker") as Worker
        model.addAttribute("workers", workerService.allWorkers())
        return "admin/allWorkers"
    }

    /// admin getting all users

    @GetMapping("/allUsers")
    fun allUsers(model: Model): String {
        val user: User = req.session.getAttribute("user") as User
        model.addAttribute("users", userService.allUsers())
        return "admin/allUsers"
    }


    /// User can get his home page after login

    @GetMapping("/userHome")
    private fun userHome(model: Model): String {
        var user: User = req.session.getAttribute("user") as User
        if (user != null) {
            model.addAttribute("user", user)
            return USER_HOME
        } else {
            return "user/login"
        }
        return USER_HOME
    }

    /// it returns the register page for the user

    @GetMapping("/userRegister")
    fun userRegister(): String {
        return USER_REG;
    }

    // it returns worker register page

    @GetMapping("/workerRegister")
    fun workerRegister(): String {
        return WORKER_REG
    }

    // it returns worker login page

    @GetMapping("/workerLogin")
    fun workerLog(): String {
        return WORKER_LOG
    }


    /// it returns the home page for the worker
    @GetMapping("/workerHome")
    fun workerHome(model: Model): String {
        var worker: Worker = req.session.getAttribute("worker") as Worker
        if (worker != null) {
            model.addAttribute("worker", worker)
            return WORKER_HOME
        } else {
            return "worker/login"
        }
    }


}