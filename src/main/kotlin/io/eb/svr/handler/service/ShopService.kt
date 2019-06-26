package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.entity.Store
import io.eb.svr.model.repository.StoreRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Create by lucy on 2019-06-10
 **/
@Service
class ShopService {
	companion object : KLogging()

	@Autowired
	private lateinit var storeRepository: StoreRepository

	@Throws(CustomException::class)
	fun searchShopById(storeId: Long) : Store {
		return storeRepository.getOne(storeId)
	}

	fun createShop(store: Store) : Long {
		return storeRepository.save(store).id!!
	}
}