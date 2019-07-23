package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.eb.svr.model.enums.ServiceAppointmentStatus
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.TypeDef
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Create by lucy on 2019-07-23
 **/
@Entity
@Table(schema = "core",
	name = "service_appointment",
	indexes = [
		Index(columnList = "shop_id", unique = false)
	])
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceAppointment (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	var id: Long,

	@Column(name="shop_id", nullable=false, insertable = true, updatable = false)
	var shopId: Long,

	@Column(name="employee_id", nullable=false, insertable = true, updatable = false)
	var employeeId: Long,

	@Column(name="customer_id", nullable=false, insertable = true, updatable = false)
	var customerId: Long,

	@Column(name="product_id", nullable=false, insertable = true, updatable = false)
	var productId: Long,

	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable = false, unique = false)
	var status: ServiceAppointmentStatus,

	@Column(name = "start_date", nullable=false, insertable = true, updatable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	var startDate: LocalDateTime,

	@Column(name = "end_date", nullable=false, insertable = true, updatable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	var endDate: LocalDateTime,

	@Column(name = "duration", nullable=false, insertable = true, updatable = true)
	var duration: Long
): Auditable(), Serializable {

}

