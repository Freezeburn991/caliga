package com.bluedragon.model

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = uuid("id").primaryKey()
    val firstname = text("firstname")
    val lastname = text("lastname")
    val username = text("username")
    val password = text("password")
    val salt = text("salt")
    val email = text("email")
}