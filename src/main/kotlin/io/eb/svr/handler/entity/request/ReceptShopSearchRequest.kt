package io.eb.svr.handler.entity.request

import java.util.*
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-06-25
 **/
data class ReceptShopSearchRequest (
	@NotNull
	val startDate: Date,

	@NotNull
	val EndDate: Date,

	val confirmYn: String? = null,
	val deleteYn: String? = null
)