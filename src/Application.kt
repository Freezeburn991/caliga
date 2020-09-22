package com.bluedragon

import com.bluedragon.auth.JwtService
import com.bluedragon.auth.MySession
import com.bluedragon.auth.hash
import com.bluedragon.controllers.UserController
import com.bluedragon.model.User
import com.bluedragon.routes.users
import com.bluedragon.services.DatabaseFactory
import com.bluedragon.services.DatabaseFactory.initDB
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
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.receive
import io.ktor.server.netty.EngineMain
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import java.util.*



fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Locations) {
    }

    install(Sessions){
        cookie<MySession>("MY_SESSION"){
            cookie.extensions["SameSite"] = "lax"
        }
    }

    //This code initializes the data layer
    initDB()
    val db = UserController()

    // Handles authentication
    val jwtService = JwtService()
    val hashFunction = {s: String -> hash(s)}

    install(Authentication) {
        jwt ("jwt"){
            verifier(jwtService.verifier)
            realm = "Blue Dragon Server"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = db.findUser(claimString)
                user
            }
        }

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


    routing {
        users(db, jwtService, hashFunction)

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

const val API_VERSION = "/v1"
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

