package com.bluedragon.controllers

import com.bluedragon.model.User
import com.bluedragon.model.UserDTO
import com.bluedragon.model.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserController {

    fun getAll(): ArrayList<User> {
        val users: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                users.add(
                    User(
                        id = it[Users.id],
                        firstName = it[Users.firstname],
                        lastName = it[Users.lastname],
                        username = it[Users.username],
                        password = it[Users.password],
                        salt = it[Users.salt],
                        email = it[Users.email]
                    )
                )
            }
        }
        return users
    }

    fun insert(user: UserDTO){
        transaction {
            Users.insert{
                it[id] = UUID.randomUUID()
                it[firstname] = user.firstName
                it[lastname] = user.lastName
                it[username] = user.username
                it[password] = user.password
                it[email] = user.email
                it[salt] = user.salt
            }
        }
    }
}