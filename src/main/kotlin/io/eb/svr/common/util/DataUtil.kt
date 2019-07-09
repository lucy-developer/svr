package io.eb.svr.common.util

/**
 * Create by lucy on 2019-07-09
 **/
object DataUtil {
	fun ifNonNullString(str1: String? = null, str2: String? = null) : String? {

		if (str1.isNullOrEmpty())
			return str2
		if (str1.isNullOrEmpty())
			return null
		return str1
	}

	fun ifNonNullLong(slong: Long, tlong: Long) : Long {
		if (slong <= 0)
			return tlong
		return slong
	}

	fun ifNonNullDouble(sDouble: Double, tDouble: Double) : Double {
		if (sDouble <= 0.0)
			return tDouble
		return sDouble
	}
}