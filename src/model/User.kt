package com.bluedragon.model

import io.ktor.auth.Principal
import java.io.Serializable
import java.util.*

data class User (val id: Int,
                 val firstName: String,
                 val lastName: String,
                 val username: String,
                 val password: String,
                 val email: String) : Serializable, Principal



/*data class UserDTO(
    val firstName: String,
    val lastName: String,
    val username: String,
    var password: String,
    var salt: String,
    val email: String
)*/