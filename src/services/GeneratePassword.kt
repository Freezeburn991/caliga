package com.bluedragon.services

import com.bluedragon.model.User
import org.mindrot.jbcrypt.BCrypt
import java.security.NoSuchAlgorithmException
import  java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.util.Optional
import java.util.Arrays

class GeneratePassword {



    /*
    fun generatePasswordForUser(userDTO: UserDTO): UserDTO {

        var salt: String = generateSalt().get()
        var password: String = userDTO.password
        var key: String = hashPassword(password, salt).get()
        userDTO.password = key
        userDTO.salt = salt
        return userDTO

    }


    private val RAND: SecureRandom = SecureRandom()
    private val SALTLENGTH: Int = 512
    private val ITERATIONS = 65536
    private val KEY_LENGTH = 512
    private val ALGORITHM = "PBKDF2WithHmacSHA512"

    fun generateSalt(): Optional<String> {

        val salt = ByteArray(SALTLENGTH)
        RAND.nextBytes(salt)

        return Optional.of(Base64.getEncoder().encodeToString(salt))

    }

    fun hashPassword(password: String, salt: String): Optional<String> {


        val chars = password.toCharArray()
        val bytes = salt.toByteArray()

        val spec = PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH)

        Arrays.fill(chars, Character.MIN_VALUE)

        try {
            val fac = SecretKeyFactory.getInstance(ALGORITHM)
            val securePassword = fac.generateSecret(spec).encoded
            return Optional.of(Base64.getEncoder().encodeToString(securePassword))

        } catch (ex: NoSuchAlgorithmException) {
            System.err.println("Exception encountered in hashPassword()")
            return Optional.empty()

        } catch (ex: InvalidKeySpecException) {
            System.err.println("Exception encountered in hashPassword()")
            return Optional.empty()
        } finally {
            spec.clearPassword()
        }

    }

    fun verifyPassword(password: String, key: String, salt: String): Boolean {
        val optEncrypted = hashPassword(password, salt)
        return if (!optEncrypted.isPresent) false else optEncrypted.get() == key
    }*/
}
