package io.eb.svr.exception

import org.springframework.http.HttpStatus

/**
 * Create by lucy on 2019-07-16
 **/
class ResourceNotFoundException (
	override val message: String
) : RuntimeException(message)