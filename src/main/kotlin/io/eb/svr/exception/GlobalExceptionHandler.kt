package io.eb.svr.exception

import javassist.NotFoundException
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @Bean
    fun errorAttributes(): ErrorAttributes = object : DefaultErrorAttributes() {
        override fun getErrorAttributes(
            request: WebRequest,
            trace: Boolean
        ): Map<String, Any> = super.getErrorAttributes(request, trace).apply {
            remove("exception")
        }
    }

    @ExceptionHandler(CustomException::class)
    @Throws(IOException::class)
    fun handleCustomException(response: HttpServletResponse, exception: CustomException) {
        response.sendError(exception.status.value(), exception.message)
    }

    @ExceptionHandler(AlreadyExistsException::class)
    @Throws(IOException::class)
    fun handleAlreadyExistsException(response: HttpServletResponse, exception: AlreadyExistsException) {
        response.sendError(exception.status.value(), exception.data.toString())
    }

    @ExceptionHandler(AccessDeniedException::class)
    @Throws(IOException::class)
    fun handleAccessDeniedException(response: HttpServletResponse, exception: AccessDeniedException) {
        response.sendError(UNAUTHORIZED.value(), "Access denied")
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun handleException(response: HttpServletResponse, exception: Exception) {
        response.sendError(BAD_REQUEST.value(), "Something went wrong")
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @Throws(IOException::class)
    fun handleResourceNotFoundException(response: HttpServletResponse, exception: ResourceNotFoundException) {
        response.sendError(NOT_FOUND.value(), exception.message)
    }
}
