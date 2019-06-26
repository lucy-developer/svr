package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.ServiceType
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank

/**
 * Create by lucy on 2019-06-18
 **/
data class ShopReceptSearchRequest (
	@NotBlank
	@Enumerated(EnumType.STRING) val serviceType: ServiceType,

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
