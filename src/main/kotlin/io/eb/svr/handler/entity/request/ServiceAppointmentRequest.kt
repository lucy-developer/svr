package io.eb.svr.handler.entity.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-23
 **/
data class ServiceAppointmentRequest (
	@NotNull
	val userId: Long,

	@NotNull
	val shopId: Long,

	@NotNull
	val productId: Long,

	@NotNull
	val employeeId: Long,

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	val appointmentDate: String,

	val duration: Long
)