package com.example.homeservices.controller

import com.example.homeservices.model.GetOrder
import com.example.homeservices.model.User
import com.example.homeservices.service.IGetOrderService
import com.example.homeservices.service.IUserService
import com.example.homeservices.service.IWorkerService
import com.example.homeservices.service.impl.GetOrderService
import com.example.homeservices.service.impl.UserService
import jakarta.persistence.criteria.Order
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController(
    var userService: IUserService,
    var workerService: IWorkerService,
    var req: HttpServletRequest,
    var getOrderService: IGetOrderService
) {

    // Here we  are creating shortcut for pages

    private val ADMIN_HOME: String = "admin/home"
    private var USER_LOG: String = "user/login"
    private var USER_REG: String = "user/register"


    /// User can register with own credentials
    /// Here user have some conditions to get registration
    /// By through Below conditions user can register


    @PostMapping("/userRegister")
    private fun userReg(model: Model, name: String, email: String, password: String,phoneNumber:Long): String {
        if (email == "" || password == "" || name == "" ) {
            model.addAttribute("error", "please fill out all the fields")
        } else {
            val user: User = User(name = name, email = email, password = password, phoneNumber = phoneNumber)
            if (userService.getByEmail(user.email) != null) {
                model.addAttribute("error", "user already exist");
            } else {
                userService.addUser(user)
                model.addAttribute("success", "registration done successfully")
                req.session.setAttribute("user", user)
                return USER_LOG
            }

        }
        return USER_REG;
    }


    /// User can login with registered credentials
    // By through below conditions

    @PostMapping("/userLogin")
    private fun useLogin(model: Model, email: String, password: String): String {
        if (email == "admin@gmail.com" && password == "admin") {
//            val user: User = req.session.getAttribute("user") as User
            model.addAttribute("success", "admin login successfully")
            return ADMIN_HOME
        }
        if (email == "" || password == "") {
            model.addAttribute("error", "please fil out all the fields")
        } else {
            val user: User? = userService.getByEmailAndPassword(email, password)
            if (user != null) {
                req.session.setAttribute("user", user)
                return "redirect:/userHome"
            }
        }
        return USER_LOG;
    }


    /// User can book worker his own requirements


    @PostMapping("/order")
    fun order(model: Model, workerName: String, workType: String): String {
        val user: User = req.session.getAttribute("user") as User
        val getOrder: GetOrder = GetOrder(userName = user.name, workerName = workerName, workType = workType, userNumber = user.phoneNumber)
        getOrderService.addOrder(getOrder)
        model.addAttribute("success","Order booked successfully")
        req.session.setAttribute("getOrder", getOrder)
        return "user/allWorkers"
    }

    // we are getting all users

    @GetMapping("/users")
    fun allUsers(): ResponseEntity<*> {
        try {
            return ResponseEntity(userService.allUsers(), HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // we are getting selected user

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> {
        try {
            return ResponseEntity(userService.getById(id), HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // we are adding user with required details
    @PostMapping("/users")
    fun addUsers(@RequestBody user: User): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        return try {
            userService.addUser(user)
            res["success"] = "Details added successfully"
            ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res["error"] = "Details not added"
            ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // we are updating the details


    @PatchMapping("/users/{id}")
    fun updateUser(@RequestBody user: User, @PathVariable id: Long): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        try {
            val u: User = userService.getById(id)

            val updatedUser = userService.updateUser(
                u.copy(
                    name = user.name ?: u.name,
                    email = user.email ?: u.email,
                    password = user.password ?: u.password,
                    phoneNumber = user.phoneNumber ?: u.phoneNumber
                )
            )
            userService.updateUser(updatedUser)
            res.put("success", "user updated successfully")
            return ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res.put("error", "user not updated")
            return ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // we are deleting selected user


    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        try {
            userService.deleteUser(id)
            res.put("success", "user deleted successfully")
            return ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res.put("error", "User not deleted")
            return ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}