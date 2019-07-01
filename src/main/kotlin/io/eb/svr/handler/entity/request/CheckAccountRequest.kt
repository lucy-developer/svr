package io.eb.svr.handler.entity.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-06-28
 **/
data class CheckAccountRequest (
	@NotBlank
	val email: String,

	@NotBlank
	val name: String,

	@NotBlank
	val mobile1: String,

	@NotBlank
	val mobile2: String,

	@NotBlank
	val mobile3: String
)