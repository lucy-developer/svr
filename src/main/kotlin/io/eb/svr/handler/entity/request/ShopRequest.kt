package io.eb.svr.handler.entity.request

import javax.validation.constraints.NotBlank

/**
 * Create by lucy on 2019-06-28
 **/
data class ShopRequest(
	@NotBlank
	val shopId: Long,

	val zip: String? = null,
	val address: String? = null,
	val addressDetail: String? = null,

	val shopName: String? = null,

	val latitude: Double? = null,
	val longitude: Double? = null,

	val phone1: String? = null,
	val phone2: String? = null,
	val phone3: String? = null,

	val city: String? = null,
	val district: String? = null
)