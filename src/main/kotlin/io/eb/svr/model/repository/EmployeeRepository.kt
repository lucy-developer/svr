package io.eb.svr.model.repository

import io.eb.svr.model.entity.EmployeeHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Create by lucy on 2019-07-15
 **/
@Repository
interface EmployeeHistoryRepository: JpaRepository<EmployeeHistory, Long> {
	fun findTopByUserIdAndShopIdOrderByStartDateDesc(userId: Long, shopId: Long) : EmployeeHistory?
}