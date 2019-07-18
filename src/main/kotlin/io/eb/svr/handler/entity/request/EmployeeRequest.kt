package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.EmployeeStatus
import io.eb.svr.model.enums.Position
import io.eb.svr.model.enums.ShopRole
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-10
 **/
data class EmployeeRequest (
	@NotNull
	val userId: Long,

	@NotNull
	val shopId: Long,

	val nickName: String? = null,

	@Enumerated(EnumType.STRING) val position: Position? = null,
	@Enumerated(EnumType.STRING) val role: ShopRole? = null,

	val date: LocalDate? = null,
	@Enumerated(EnumType.STRING) val status: EmployeeStatus? = null,

	val daysWorkLists: List<DaysList>? = null
)
