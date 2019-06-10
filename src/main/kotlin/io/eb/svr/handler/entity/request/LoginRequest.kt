package io.eb.svr.handler.entity.request

data class LoginRequest(
    val username: String,
    val password: String
)