package com.bluedragon.Repository

import com.bluedragon.model.User

interface Repository {
    suspend fun addUser(email: String,
                        firstName: String,
                        lastName: String,
                        username: String,
                        passwordHash: String): User?
    suspend fun deleteUser(userId: Int)
    suspend fun findUser(userId: Int): User?
    suspend fun findUserByEmail(email: String): User?
}