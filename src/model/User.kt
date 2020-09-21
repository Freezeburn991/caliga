package com.bluedragon.model

import java.util.*

data class User (val id: UUID = UUID.randomUUID(), val firstName: String, val lastName: String, val username: String, val password: String, val salt: String, val email: String)



data class UserDTO(
    val firstName: String,
    val lastName: String,
    val username: String,
    var password: String,
    var salt: String,
    val email: String
)