package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.Days
import io.eb.svr.model.enums.TimeType
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-09
 **/
data class ShopOperationTimeRequest(
	@NotNull
	val shopId: Long,

	@NotNull
	@Enumerated(EnumType.STRING)
	val timeType: TimeType,

	@NotNull
	val option: String,

	val startTime: String? = null,
	val endTime: String? = null,

	val daysLists: List<DaysList>? = null
)

data class DaysList (
	@Enumerated(EnumType.STRING)
	val days: Days? = null,
	val startTime: String? = null,
	var endTime: String? = null
)