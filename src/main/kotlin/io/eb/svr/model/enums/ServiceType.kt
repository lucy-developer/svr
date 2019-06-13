package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-06-13
 **/
enum class ServiceType(val type: String, val desc: String) {
	HAIR("HAIR", "헤어샵"),
	NAIL("NAIL", "네일샵"),
	MASSAGE("MASSAGE", "마사지"),
	ETC("ETC", "기타")
}