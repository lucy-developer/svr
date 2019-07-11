package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-07-11
 **/
enum class EmployeeStatus(val type: String, val desc: String) {
	WORK("WORK", "재직"),
	LEAVE("LEAVE", "퇴사"),
	DAYOFF("DAYOFF", "휴직")
}