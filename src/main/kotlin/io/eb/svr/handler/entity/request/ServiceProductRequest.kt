package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.*
import java.time.LocalDateTime
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-18
 **/
data class ServiceProductRequest (
	@NotNull
	val shopId: Long,

	@NotNull
	val productId: Long,

	@NotNull
	val name: String,

	@Enumerated(EnumType.STRING) val type: ProductType? = null,
	@Enumerated(EnumType.STRING) val cls: ProductCls? = null,

	val originPrice: Long? = null,
	val discountPrice: Long? = null,
	val salePrice: Long? = null,

	val duration: Long? = null,
	@Enumerated(EnumType.STRING) val target: TargetType? = null,
	val description: String? = null,

	val managers: MutableSet<String>? = null,

	val onlineYn: String? = null,
	val onlineDate: LocalDateTime? = null,

	val sales: List<ProductSaleList>? = null
)

data class ProductSaleList (
	@Enumerated(EnumType.STRING) val type: ProductSaleType,
	val option1: String? = null,
	val option2: String? = null,
	val option3: String? = null,

	val discountPrice: Long,
	val salePrice: Long,

	@Enumerated(EnumType.STRING) val eventType: EventType,
	val eventOption: MutableSet<String>? = null
)