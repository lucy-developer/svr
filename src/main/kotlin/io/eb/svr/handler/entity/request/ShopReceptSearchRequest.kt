package io.eb.svr.handler.entity.request

import javax.validation.constraints.NotBlank

/**
 * Create by lucy on 2019-06-18
 **/
data class ShopReceptSearchRequest (
	@NotBlank
	val shopName: String,

	@NotBlank
	val name: String,

	@NotBlank
	val mobile1: String,

	@NotBlank
	val mobile2: String,

	@NotBlank
	val mobile3: String
)
