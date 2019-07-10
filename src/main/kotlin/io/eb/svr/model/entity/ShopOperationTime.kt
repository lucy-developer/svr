package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.eb.svr.model.enums.Days
import io.eb.svr.model.enums.TimeType
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * Create by lucy on 2019-02-19
 **/
@Entity
@Table(schema = "core", name = "shop_operation_time")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class ShopOperationTime (
	@EmbeddedId
	var pk: ShopOperationTimePK,

	@Column(name = "start_time")
	var startTime: String,

	@Column(name = "end_time")
	var endTime: String

) : Auditable(), Serializable {
}

@Embeddable
data class ShopOperationTimePK (
	@Column(name = "shop_id")
	var shopId: Long,

	@Column(name = "start_date")
	var startDate: LocalDateTime,

	@Column(name = "end_date")
	var endDate: LocalDateTime,

	@Enumerated(EnumType.STRING)
	@Column(name = "type_code")
	var typeCode: TimeType,

	@Enumerated(EnumType.STRING)
	@Column(name = "day_code")
	var dayCode: Days
) : Serializable