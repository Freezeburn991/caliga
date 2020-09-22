package com.bluedragon.controllers

import com.bluedragon.Repository.Repository
import com.bluedragon.model.User
import com.bluedragon.Repository.Users
import com.bluedragon.services.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserController: Repository{

     override suspend fun addUser(email: String, firstName: String, lastName: String, username: String, passwordHash: String): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery{
            statement = Users.insert { user ->
                user[firstname] = firstName
                user[lastname] = lastName
                user[Users.username] = username
                user[password] = passwordHash
                user[Users.email] = email

            }
        }
        return rowToUser(statement?.resultedValues?.get(0))
    }

    private fun rowToUser(row: ResultRow?): User?{
        if(row == null) return null
        return User(
            id =  row[Users.id],
            firstName = row[Users.firstname],
            lastName = row[Users.lastname],
            username = row[Users.username],
            password = row[Users.password],
            email = row[Users.email]

        )
    }

    override suspend fun deleteUser(userId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findUser(userId: Int): User? = dbQuery {
        Users.select { Users.id.eq(userId) }
            .map { rowToUser(it) }.singleOrNull()
    }

    override suspend fun findUserByEmail(email: String): User? = dbQuery{
        Users.select { Users.email.eq(email) }
            .map { rowToUser(it) }.singleOrNull()
    }

    /* fun getAll(): ArrayList<User> {
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
     }*/

  /*  fun insert(user: UserDTO){
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
    }*/
}