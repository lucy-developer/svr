package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
//import io.eb.svr.model.entity.types.json.JsonBinaryType
import io.eb.svr.model.enums.*
import org.hibernate.annotations.*
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Create by lucy on 2019-07-18
 **/
@Entity
@Table(schema = "core",
	name = "service_product")
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ServiceProduct (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	var id: Long,

	@Column(name="shop_id", nullable=false, insertable = true, updatable = false)
	var shopId: Long,

	@Column(name="name", nullable=false, unique = false)
	var name: String,

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, unique = false)
	var type: ProductType,

	@Enumerated(EnumType.STRING)
	@Column(name = "cls", nullable = false, unique = false)
	var cls: ProductCls,

	@Column(name = "origin_price", nullable = false, unique = false)
	var originPrice: Long,

	@Column(name = "discount_price", nullable = true)
	var discountPrice: Long? = null,

	@Column(name = "sale_price", nullable = false, unique = false)
	var salePrice: Long,

	@Column(name = "duration", nullable = true)
	var duration: Long? = null,

	@Enumerated(EnumType.STRING)
	@Column(name = "target", nullable = false, unique = false)
	var target: TargetType,

	@Column(name = "description")
	var description: String? = null,

	@Type(type = "jsonb")
	@Column(name = "managers", columnDefinition = "jsonb")
	var managers: MutableSet<String> = mutableSetOf(),

	@Column(name = "online_yn", nullable = false, unique = false)
	var onlineYn: String = "N",

	@Column(name = "online_date")
	var onlineDate: LocalDateTime? = null,

	@Column(name = "delete_yn", nullable = false)
	var deleteYn: String
): Auditable(), Serializable {

	@OneToMany//(mappedBy = "serviceProduct", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@Where(clause = "delete_yn = 'N'")
	var serviceProductSales: List<ServiceProductSale> = emptyList()
}

@Entity
@Table(schema = "core",
	name = "service_product_sale",
	uniqueConstraints = [
		UniqueConstraint(columnNames = ["product_id", "type", "event_type", "delete_yn"])])
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ServiceProductSale (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	var id: Long,

	@Column(name = "product_id", insertable = true, updatable = false)
	var productId: Long,

	@Enumerated(EnumType.STRING)
	@Column(name = "type", insertable = true, updatable = false)
	var type: ProductSaleType,

	@Column(name = "option_1", nullable = true, unique = false)
	var option1: String? = null,

	@Column(name = "option_2", nullable = true, unique = false)
	var option2: String? = null,

	@Column(name = "option_3", nullable = true, unique = false)
	var option3: String? = null,

	@Column(name = "discount_price", nullable = true)
	var discountPrice: Long,

	@Column(name = "sale_price", nullable = false, unique = false)
	var salePrice: Long,

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type", nullable = false, unique = false)
	var eventType: EventType,

	@Type(type = "jsonb")
	@Column(name = "event_option", columnDefinition = "jsonb")
	var eventOption: MutableSet<String>? = mutableSetOf(),

	@Column(name = "delete_yn", nullable = false)
	var deleteYn: String = "N"
): Auditable(), Serializable {

}