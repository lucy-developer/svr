package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-07-09
 **/
enum class TimeType(val type: String, val desc: String) {
	WORK("WORK", "근무시간"),
	OPERATION("OPERATION", "운영시간"),
	RESERVATION("RESERVATION", "예약시간")
}

enum class Days(val type: String, val desc: String) {
	MON("MON", "월요일"),
	TUE("TUE", "화요일"),
	WED("WED", "수요일"),
	TUR("TUR", "목요일"),
	FRI("FRI", "금요일"),
	SAT("SAT", "토요일"),
	SUN("SUN", "일요일")
}