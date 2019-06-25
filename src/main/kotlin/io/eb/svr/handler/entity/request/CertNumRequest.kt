package io.eb.svr.handler.entity.request

import javax.validation.constraints.NotBlank

/**
 * Create by lucy on 2019-06-13
 **/
data class CertNumRequest (
	@NotBlank
	val mobile1: String,

	@NotBlank
	val mobile2: String,

	@NotBlank
	val mobile3: String,

	@NotBlank
	val number: String
)