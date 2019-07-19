package io.eb.svr.handler.service

import io.eb.svr.exception.AlreadyExistsException
import io.eb.svr.exception.CustomException
import io.eb.svr.exception.ResourceNotFoundException
import io.eb.svr.handler.entity.request.ServiceProductRequest
import io.eb.svr.model.entity.ServiceProduct
import io.eb.svr.model.entity.ServiceProductSale
import io.eb.svr.model.enums.ProductSaleType
import io.eb.svr.model.repository.ServiceProductRepository
import io.eb.svr.model.repository.ServiceProductSaleRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-07-18
 **/
@Service
class ProductService {
	companion object : KLogging()

	@Autowired
	private lateinit var serviceProductRepository: ServiceProductRepository

	@Autowired
	private lateinit var serviceProductSaleRepository: ServiceProductSaleRepository

	@Autowired
	private lateinit var shopService: ShopService

	@Throws(CustomException::class)
	fun getServiceProductByShopId(servlet: HttpServletRequest, shopId: Long) : List<ServiceProduct> {
		return serviceProductRepository.findServiceProductsByShopId(shopId)
	}

	@Throws(ResourceNotFoundException::class, AlreadyExistsException::class)
	fun createServiceProduct(servlet: HttpServletRequest, request: ServiceProductRequest) = with(request) {
		if (!shopService.checkIfStoreIdIsExist(shopId)) {
			throw ResourceNotFoundException("Shop '$shopId' not found")
		}

		if (checkIfShopIdServiceProductIsExist(shopId, name)) {
			throw AlreadyExistsException("Shop '$shopId' product '$name' is already  not found", HttpStatus.CONFLICT)
		}

		var serviceProduct = ServiceProduct(
			id = -1,
			shopId = shopId,
			name = name,
			type = type!!,
			cls = cls!!,
			originPrice = originPrice!!,
			discountPrice = discountPrice!!,
			salePrice = salePrice!!,
			duration = duration,
			target = target!!,
			description = description,
			managers = managers!!,
			onlineYn = onlineYn!!,
			onlineDate = onlineDate,
			deleteYn = "N" )

		serviceProduct = serviceProductRepository.save(serviceProduct)

		sales?.let {
			for (sale in sales) {
				val newServiceProductSale = ServiceProductSale(
					id = -1,
					productId = serviceProduct.id,
					type = sale.type,
					option1 = sale.option1,
					option2 = sale.option2,
					option3 = sale.option3,
					discountPrice = sale.discountPrice,
					salePrice = sale.salePrice,
					eventType = sale.eventType,
					eventOption = sale.eventOption,
					deleteYn = "N"
				)
				serviceProductSaleRepository.save(newServiceProductSale)
			}
		}
	}

	fun checkIfShopIdServiceProductIsExist(shopId: Long, name: String) : Boolean {
		serviceProductRepository.findServiceProductsByShopIdAndNameAndDeleteYn(shopId, name, "N")?.let { return true }
		return false
	}
}