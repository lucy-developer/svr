package io.eb.svr.common.util

import java.util.HashMap



/**
 * Create by lucy on 2019-06-10
 **/
interface BaseEnum<T> {
	val code: T
	val name: String

	companion object {
		fun <E : BaseEnum<T>, T> getEnum(enumClass: Class<E>, enumValue: T?): E? {
			if (enumValue == null) {
				return null
			}
			try {
				return valueOf(enumClass, enumValue)
			} catch (ex: IllegalArgumentException) {
				return null
			}

		}

		fun <E : BaseEnum<T>, T> valueOf(enumClass: Class<E>, enumValue: T?): E {
			if (enumValue == null) throw NullPointerException("EnumValue is null")
			return getEnumMap(enumClass)[enumValue]!!
		}

		fun <E : BaseEnum<T>, T> getEnumMap(enumClass: Class<E>): Map<T, E> {
			val enums = enumClass.enumConstants
				?: throw IllegalArgumentException(enumClass.simpleName + " does not represent an enum type.")
			val map = HashMap<T, E>(2 * enums.size)
			for (t in enums) {
				map[t.code] = t
			}
			return map
		}
	}
}