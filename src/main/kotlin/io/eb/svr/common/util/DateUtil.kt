package io.eb.svr.common.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Create by lucy on 2019-06-14
 **/
//@SuppressLint("SimpleDateFormat")
object DateUtil {

	private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

	val nowDateTime: String
		get() {
			val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			return sdf.format(Date())
		}

	val nowDate: String
		get() {
			val sdf = SimpleDateFormat("yyyy-MM-dd")
			return sdf.format(Date())
		}

	val nowTime: String
		get() {
			val sdf = SimpleDateFormat("HH:mm:ss")
			return sdf.format(Date())
		}

	val nowTimeDetail: String
		get() {
			val sdf = SimpleDateFormat("HH:mm:ss.SSS")
			return sdf.format(Date())
		}

	fun getFormatTime(format: String=""): String {
		val ft: String = format
		val sdf = if (!ft.isEmpty()) SimpleDateFormat("format")
		else SimpleDateFormat("yyyyMMddHHmmss")
		return sdf.format(Date())
	}

	fun stringToLocalDateTime(dateString: String="") : LocalDateTime {
		return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	}

	fun stringToLocalDate(dateString: String="") : LocalDate {
		return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
	}

	fun formatDateTime(dateTime: LocalDateTime, timePattern: String = DATE_TIME_PATTERN): String {
		return dateTime.format(DateTimeFormatter.ofPattern(timePattern))
	}


	fun getAddDays(date: LocalDate, amount: Long): LocalDate {
		return date.plusDays(amount)
	}

	fun getAddDays(date: LocalDateTime, amount: Long): LocalDateTime {
		return date.plusDays(amount)
	}

	fun getAddMinutes(date: LocalDateTime, amount: Long): LocalDateTime {
		return date.plusMinutes(amount)
	}
}