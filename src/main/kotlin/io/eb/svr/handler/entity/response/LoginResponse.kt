package io.eb.svr.handler.entity.response

/**
 * Create by lucy on 2019-06-03
 **/
data class LoginResponse (
	val token: String,
	val userInfo: HashMap<String, Any>
)