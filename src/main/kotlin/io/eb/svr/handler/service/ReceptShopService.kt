package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.ShopReceptSearchRequest
import io.eb.svr.model.repository.ReceptStoreRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.collections.HashMap

/**
 * Create by lucy on 2019-06-13
 **/
@Service
class ReceptShopService {
	companion object : KLogging()

	@Autowired
	private lateinit var receptStoreRepository: ReceptStoreRepository

	@Autowired
	private lateinit var shopService: ShopService

	fun shopReceptSearch(request: ShopReceptSearchRequest): HashMap<String, Any> {
		val data = HashMap<String, Any>()

		val receptList = receptStoreRepository.findReceptStoresByCeoNameAndMobileAndStoreNameOrderByCreateDateDesc(
				request.shopName.replace(" ", ""), request.name, request.mobile1, request.mobile2, request.mobile3)

		var status = "N"
		if (receptList.isNotEmpty()) {
			for (recept in receptList) {
				if (recept.confirmYn == "Y") {
					val shop = shopService.searchShopById(recept.storeId!!)
					data.put("recept", recept)
					data.put("shop", shop)
					return data
				}

				if (recept.deleteYn == "Y") status = "D"
				if (recept.confirmYn == "N") status = "N"
			}
		} else
			throw CustomException("Shop Recept order not found", HttpStatus.NOT_FOUND)

		if (status == "D")
			throw CustomException("Shop Recept order deleted", HttpStatus.NOT_ACCEPTABLE)

		if (status == "N") throw CustomException("Shop Recept order Unapproved", HttpStatus.NO_CONTENT)

		logger.info { "shop info mobile1 ["+data.get("mobile")+"]"}

		return data
	}
}