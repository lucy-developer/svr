package io.eb.svr.model.repository

import io.eb.svr.model.entity.ServiceAppointment
import io.eb.svr.model.entity.ServiceProduct
import io.eb.svr.model.entity.ServiceProductSale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Create by lucy on 2019-07-18
 **/
@Repository
interface ServiceProductRepository: JpaRepository<ServiceProduct, Long> {
	fun findServiceProductsByShopId(shopId: Long): List<ServiceProduct>

	fun findServiceProductsByShopIdAndNameAndDeleteYn(shopId: Long, name: String, deleteYn: String) : ServiceProduct?
}

@Repository
interface ServiceProductSaleRepository: JpaRepository<ServiceProductSale, Long> {

}

@Repository
interface ServiceAppointmentRepository: JpaRepository<ServiceAppointment, Long> {
	fun findServiceAppointmentsByShopIdAndEmployeeIdAndStartDateBetween(
		shopId: Long, employeeId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<ServiceAppointment>

	@Query("SELECT v from ServiceAppointment v where v.shopId = :shopId and v.employeeId = :employeeId and (start_date >= :startDate or end_date <= :endDate)")
	fun checkServiceAppointments(
		shopId: Long, employeeId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<ServiceAppointment>

	fun findServiceAppointmentsByShopIdAndEmployeeIdAndStartDate(shopId: Long, employeeId: Long, startDate: LocalDateTime) : List<ServiceAppointment>
	fun countServiceAppointmentsByShopIdAndEmployeeIdAndStartDate(shopId: Long, employeeId: Long, startDate: LocalDateTime) : Long
}