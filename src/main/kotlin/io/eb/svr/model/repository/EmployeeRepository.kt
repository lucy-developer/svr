package io.eb.svr.model.repository

import io.eb.svr.model.entity.EmployeeHistory
import io.eb.svr.model.entity.EmployeeWorkTime
import io.eb.svr.model.enums.Days
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Create by lucy on 2019-07-15
 **/
@Repository
interface EmployeeHistoryRepository: JpaRepository<EmployeeHistory, Long> {
	fun findTopByUserIdAndShopIdOrderByStartDateDesc(userId: Long, shopId: Long) : EmployeeHistory?
}

@Repository
interface EmployeeWorkTimeRepository: JpaRepository<EmployeeWorkTime, Long> {
	fun findEmployeeWorkTimesByUserIdAndShopIdAndEndDate(userId: Long, shopId: Long, endDate: LocalDateTime) : List<EmployeeWorkTime>

	fun findEmployeeWorkTimesByUserIdAndShopIdAndDayAndEndDate(userId: Long, shopId: Long, day: Days, endDate: LocalDateTime) : EmployeeWorkTime?
}
