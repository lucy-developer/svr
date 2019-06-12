package io.eb.svr.model.enums

import io.eb.svr.common.util.BaseEnum


/**
 * Create by lucy on 2019-06-10
 **/
enum class Gender(val type: String, val desc: String) {
	unknown("unknown", "전체"),
	male("male", "남성"),
	female("female", "여성");

//	override fun getCode(): String = type
//	override fun getName(): String = desc

	companion object {
		fun getCode(type: String): String {
			for(g in Gender.values()) {
				if (g.type == type)
					return g.type
			}
			return ""
		}

		fun getName(type: String): String {
			for(g in Gender.values()) {
				if (g.type == type)
					return g.desc
			}
			return ""
		}

		fun getEnum(type: String?): Gender {
			if (type == null) return Gender.unknown
			enumValues<Gender>().forEach {
				if (it.type == type) return@getEnum it
			}
			return Gender.unknown
		}
	}
}

inline fun <reified T : Enum<T>> getEnum(code: String?): T? {
	if (code == null) return null
	enumValues<T>().forEach {
		if (it.name == code) return@getEnum it
	}
	return null
}
