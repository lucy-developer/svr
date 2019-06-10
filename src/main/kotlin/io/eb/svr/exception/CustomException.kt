package io.eb.svr.exception

import org.springframework.http.HttpStatus

class CustomException(
    override val message: String,
    val status: HttpStatus
) : RuntimeException(message)