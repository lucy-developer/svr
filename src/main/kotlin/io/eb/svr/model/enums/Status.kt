package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-07-11
 **/
enum class EmployeeStatus(val type: String, val desc: String) {
	ING("ING", "재직"),
	OUT("OUT", "퇴사"),
	STOP("STOP", "휴직"),
	REING("REING", "복직")
}

enum class ServiceAppointmentStatus(val type: String, val desc: String) {
	BOOKING("BOOKING", "예약"),
	COMPLETE("COMPLETE", "시술"),
	NOSHOW("NOSHOW", "노쇼"),
	CANCEL("CANCEL", "취소")
}

