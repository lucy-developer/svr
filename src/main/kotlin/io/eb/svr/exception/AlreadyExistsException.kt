package io.eb.svr.exception

import org.springframework.http.HttpStatus

/**
 * Create by lucy on 2019-06-28
 **/
class AlreadyExistsException(
	override val message: String,
	val status: HttpStatus
) : Exception()