package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.ServiceType
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank

/**
 * Create by lucy on 2019-06-10
 **/
data class ShopReceptRequest (
	@Enumerated(EnumType.STRING) val serviceType: ServiceType,

	@NotBlank
	val city: String,

	@NotBlank
	val district: String,

	@NotBlank
	val storeName: String,

	@NotBlank
	val userName: String,

	val job: String? = null,

	val phone1: String? = null,
	val phone2: String? = null,
	val phone3: String? = null,

	@NotBlank
	val mobile1: String,

	@NotBlank
	val mobile2: String,

	@NotBlank
	val mobile3: String,

	val ceoName: String,
	val ceoMobile1: String,
	val ceoMobile2: String,
	val ceoMobile3: String
)
