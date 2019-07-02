package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.entity.B2BUserShop
import io.eb.svr.model.entity.Store
import io.eb.svr.model.repository.B2BUserShopRepository
import io.eb.svr.model.repository.StoreRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * Create by lucy on 2019-06-10
 **/
@Service
class ShopService {
	companion object : KLogging()

	@Autowired
	private lateinit var storeRepository: StoreRepository

	@Autowired
	private lateinit var b2BUserShopRepository: B2BUserShopRepository


	@Throws(CustomException::class)
	fun searchShopById(storeId: Long): Store {
		return storeRepository.getOne(storeId)
	}

	fun createShop(store: Store): Long {
		return storeRepository.save(store).id!!
	}

	@Throws(CustomException::class)
	fun createB2BUserInShop(b2BUserShop: B2BUserShop): B2BUserShop {
		return b2BUserShopRepository.save(b2BUserShop)
	}

	@Throws(CustomException::class)
	fun searchB2BUserShopByUserId(
		userId: Long,
		startDate: LocalDate,
		endDate: LocalDate
	): B2BUserShop? {
		return b2BUserShopRepository.findB2BUserShopsByB2BUserShopPKUserIdAndJoinDateLessThanEqualAndLeaveDateGreaterThanEqual(
			userId, startDate, endDate
		)
	}
}