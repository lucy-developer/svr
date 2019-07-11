package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.EmployeeRequest
import io.eb.svr.model.entity.B2BUserShop
import io.eb.svr.model.enums.EmployeeStatus
import io.eb.svr.model.repository.B2BUserShopRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-07-11
 **/
@Service
class EmployeeService {
	companion object : KLogging()

	@Autowired
	private lateinit var b2BUserShopRepository: B2BUserShopRepository

	@Throws(CustomException::class)
	fun createB2BUserInShop(b2BUserShop: B2BUserShop): B2BUserShop {
		return b2BUserShopRepository.save(b2BUserShop)
	}

	@Throws(CustomException::class)
	fun getShopEmployees(servlet: HttpServletRequest, shopId: Long) : List<B2BUserShop> {
		return b2BUserShopRepository.findB2BUserShopsByB2BUserShopPKStoreId(shopId)
	}

	@Throws(CustomException::class)
	fun putEmployee(servlet: HttpServletRequest, request: EmployeeRequest) {
		val userShop = try {
			b2BUserShopRepository.findB2BUserShopsByB2BUserShopPK(B2BUserShop.B2BUserShopPK(request.userId, request.shopId))
		} catch (exception: EntityNotFoundException) {
			throw CustomException("Shop User not found", HttpStatus.NOT_FOUND)
		}

		request.status.let {
			if (request.status == EmployeeStatus.ING) {
				userShop!!.confirmYn = "Y"
			}
		}
		request.position.let { userShop!!.position = request.position }
		request.joinDate.let { userShop!!.joinDate = request.joinDate }
		request.role.let { userShop!!.shopRole = request.role!! }

		createB2BUserInShop(userShop!!)
	}
}