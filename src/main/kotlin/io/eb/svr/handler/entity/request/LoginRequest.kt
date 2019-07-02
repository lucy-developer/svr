package io.eb.svr.handler.entity.request

data class LoginRequest(
    val email: String,
    val password: String
)