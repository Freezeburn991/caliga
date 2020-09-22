package com.bluedragon.services

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun initDB(){
        val config = HikariConfig("/hikari.properties")
        config.schema = "public"
        val ds = HikariDataSource(config)
        Database.connect(ds)
    }

    suspend fun <T> dbQuery(block: () -> T) : T =
        withContext(Dispatchers.IO){
            transaction { block() }
        }
}