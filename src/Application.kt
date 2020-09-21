package com.bluedragon

import com.bluedragon.controllers.UserController
import com.bluedragon.model.User
import com.bluedragon.model.UserDTO
import com.bluedragon.services.GeneratePassword
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.auth.jwt.jwt
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.request.receive
import io.ktor.server.netty.EngineMain
import org.jetbrains.exposed.sql.Database
import java.util.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    val userController = UserController()

    install(Authentication) {
     /*  jwt{
           verifier(JwtConfig.verifier)
           realm = "com.bluedragon"
           validate {

           }
       }*/
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }



    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    initDB()
    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

       post("/login"){

           val post = call.receive<User>()
          // val user = users.getOrPut(post.username, post.password)


       }
        // dobro za sada napraviti jos provjere za mail ...
       post("/users"){
           val userDto = GeneratePassword.generatePasswordForUser(call.receive<UserDTO>())
           userController.insert(userDto)
           call.respond(HttpStatusCode.Created)
       }

        // Izbrisati ovo jer se može doći do svih korisnika
        get("/users"){
            call.respond(userController.getAll())
        }

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

fun initDB(){
    val config = HikariConfig("/hikari.properties")
    config.schema = "public"
    val ds = HikariDataSource(config)
    Database.connect(ds)
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

