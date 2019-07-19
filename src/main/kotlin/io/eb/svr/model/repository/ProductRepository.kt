package io.eb.svr.model.repository

import io.eb.svr.model.entity.ServiceProduct
import io.eb.svr.model.entity.ServiceProductSale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

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