package io.eb.svr.handler.service

import io.eb.svr.common.util.DateUtil
import io.eb.svr.common.util.HibernateUtil
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.EmployeeRequest
import io.eb.svr.model.entity.B2BUserShop
import io.eb.svr.model.entity.EmployeeHistory
import io.eb.svr.model.entity.EmployeeWorkTime
import io.eb.svr.model.enums.Days
import io.eb.svr.model.enums.EmployeeStatus
import io.eb.svr.model.repository.B2BUserShopRepository
import io.eb.svr.model.repository.EmployeeHistoryRepository
import io.eb.svr.model.repository.EmployeeWorkTimeRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.PersistenceContext
import javax.servlet.http.HttpServletRequest


/**
 * Create by lucy on 2019-07-11
 **/
@Service
class EmployeeService {
	companion object : KLogging()

	@Autowired
	private lateinit var b2BUserShopRepository: B2BUserShopRepository

	@Autowired
	private lateinit var employeeHistoryRepository: EmployeeHistoryRepository

	@Autowired
	private lateinit var employeeWorkTimeRepository: EmployeeWorkTimeRepository

	@Inject
	protected lateinit var entityManager: EntityManager


	@Throws(CustomException::class)
	fun createB2BUserInShop(b2BUserShop: B2BUserShop): B2BUserShop {
		return b2BUserShopRepository.save(b2BUserShop)
	}

	@Throws(CustomException::class)
	fun getShopEmployees(servlet: HttpServletRequest, shopId: Long) : List<B2BUserShop> {
		return b2BUserShopRepository.findB2BUserShopsByB2BUserShopPKStoreId(shopId)
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Throws(CustomException::class)
	fun createEmployeeHistory(employeeHistory: EmployeeHistory) : EmployeeHistory {
		return employeeHistoryRepository.save(employeeHistory)
	}

//	@Transactional(propagation = Propagation.REQUIRED)
	@Throws(CustomException::class)
	fun updateEmployeeHistory(employeeHistory: EmployeeHistory) : EmployeeHistory {
		val session = HibernateUtil.getSession(entityManager)
		val transaction = session.beginTransaction()
		employeeHistoryRepository.save(employeeHistory)
		transaction.commit()
		return employeeHistory
	}

	@Throws(CustomException::class)
	fun createEmployeeWorkTime(employeeWorkTime: EmployeeWorkTime) : EmployeeWorkTime {
		return employeeWorkTimeRepository.save(employeeWorkTime)
	}

	//	@Transactional(propagation = Propagation.REQUIRED)
	@Throws(CustomException::class)
	fun updateEmployeeWorkTime(employeeWorkTime: EmployeeWorkTime) : EmployeeWorkTime {
		val session = HibernateUtil.getSession(entityManager)
		val transaction = session.beginTransaction()
		employeeWorkTimeRepository.save(employeeWorkTime)
		transaction.commit()
		return employeeWorkTime
	}



	@Throws(CustomException::class)
	fun putEmployeeByShop(servlet: HttpServletRequest, request: EmployeeRequest) {
		val userShop = try {
			b2BUserShopRepository.findB2BUserShopsByB2BUserShopPK(B2BUserShop.B2BUserShopPK(request.userId, request.shopId))
		} catch (exception: EntityNotFoundException) {
			throw CustomException("Shop User not found", HttpStatus.NOT_FOUND)
		}

		if (request.status != null) {
			employeeHistoryRepository.findTopByUserIdAndShopIdOrderByStartDateDesc(
				userShop!!.b2BUserShopPK.userId,
				userShop.b2BUserShopPK.storeId
			)?.let { employeehist ->
				updateEmployeeHistory(employeehist.apply {
					employeehist.endDate = DateUtil.getAddDays(request.date!!, -1)
				})
			}
		}

		request.status?.let {
			if (request.status == EmployeeStatus.ING) {
				userShop!!.confirmYn = "Y"
				request.date.let { userShop.joinDate = request.date }
			}

			if (request.status == EmployeeStatus.OUT) {
				request.date.let { userShop!!.leaveDate = request.date }
			}

			val employeeHistory = EmployeeHistory(
				seq = -1,
				userId = userShop!!.b2BUserShopPK.userId,
				shopId = userShop.b2BUserShopPK.storeId,
				startDate = request.date,
				endDate = DateUtil.stringToLocalDate("9999-12-31"),
				status = if (request.status!!.equals(EmployeeStatus.REING)) EmployeeStatus.ING else request.status
			)
			createEmployeeHistory(employeeHistory)
		}

		request.daysWorkLists?.let {
			for (daylist in request.daysWorkLists) {
				employeeWorkTimeRepository.findEmployeeWorkTimesByUserIdAndShopIdAndDayAndEndDate(
					userShop!!.b2BUserShopPK.userId,
					userShop.b2BUserShopPK.storeId,
					daylist.days!!,
					DateUtil.stringToLocalDateTime("9999-12-31 23:59:59")
				)?.let { preEmployeeWorkTime ->
					updateEmployeeWorkTime(preEmployeeWorkTime.apply {
						preEmployeeWorkTime.endDate = DateUtil.stringToLocalDateTime(DateUtil.nowDate+" 23:59:59")
					})
				}

				val newEmployeeWorkTime = EmployeeWorkTime(
					seq = -1,
					userId = userShop.b2BUserShopPK.userId,
					shopId = userShop.b2BUserShopPK.storeId,
					startDate = DateUtil.getAddDays(DateUtil.stringToLocalDateTime(DateUtil.nowDate+" 00:00:00"), 1),
					endDate = DateUtil.stringToLocalDateTime("9999-12-31 23:59:59"),
					day = daylist.days,
					startTime = daylist.startTime,
					endTime = daylist.endTime
				)
				createEmployeeWorkTime(newEmployeeWorkTime)
			}
		}

		request.role?.let { userShop!!.shopRole = request.role!! }
		request.nickName?.let { userShop!!.nickName = request.nickName }
		request.position?.let { userShop!!.position = request.position }
		createB2BUserInShop(userShop!!)
	}
}