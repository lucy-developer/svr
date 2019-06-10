package io.eb.svr.security

import org.springframework.http.HttpStatus.*
import io.eb.svr.exception.CustomException
import javax.validation.Validation

object DefaultValidator {
    fun <T> validate(any: T) {
        val errors = Validation.buildDefaultValidatorFactory().validator.validate<T>(any)
        if (!errors.isEmpty()) {
            val message = errors.joinToString(", ") {
                it.message
            }

            throw CustomException(message, UNPROCESSABLE_ENTITY)
        }
    }
}