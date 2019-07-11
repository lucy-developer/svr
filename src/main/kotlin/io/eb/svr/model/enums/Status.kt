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