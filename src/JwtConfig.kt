package com.bluedragon

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.bluedragon.model.User
import java.util.*

object JwtConfig {

        private val secret: String = "8h2p^:N[/{)qJD%F6`J5fs,h4+&'%ujw*m=<qvT7BTu*ztff32yz<rN-*^!A=z[hD!55"
        private val issuer = "bluedragon.com"
        private val validityInMs = 36_000_00 *10 // 10 hours
        private val algorithm = Algorithm.HMAC512(secret)

        val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()


    /**
     * Produce a token
     */
    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("username", user.username)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)


}