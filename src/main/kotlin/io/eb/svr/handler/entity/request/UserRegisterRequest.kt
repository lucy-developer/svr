package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.Gender
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-01
 **/
data class UserRegisterRequest (
	@NotNull
	val email: String,

	@NotNull
	val mobile1: String,

	@NotNull
	val mobile2: String,

	@NotNull
	val mobile3: String,

	@NotNull
	val name: String,

	@NotNull
	val password: String,

	val id: Long? = null,

	val sex: Gender? = null
)