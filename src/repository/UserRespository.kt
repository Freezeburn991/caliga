package com.bluedragon.Repository

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id : Column<Int> = integer("id").autoIncrement().primaryKey()
    val firstname = text("firstname")
    val lastname = text("lastname")
    val username = text("username")
    val password = text("password")
    val email = text("email")
}