package io.eb.svr.handler.entity.request

import java.util.*
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-06-26
 **/
data class ConfirmReceptShopRequest (
	@NotNull
	val seq: Long,

	@NotNull
	val paidDate: Date
)
