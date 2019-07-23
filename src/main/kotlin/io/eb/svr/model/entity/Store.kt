package io.eb.svr.model.entity

import io.eb.svr.model.enums.ServiceType
import org.hibernate.annotations.DynamicInsert
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicInsert
@Table(schema = "core", name = "store",
	uniqueConstraints = arrayOf(
		UniqueConstraint(columnNames = arrayOf("id"))
	))
data class Store (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id", unique = true, nullable = false)
	var id: Long? = null,

	@Column(name = "code", unique = true, nullable = false)
	var code: String? = null,

	@Column(name = "name", unique = false, nullable = false)
	var name: String? = null,

	@Column(name = "business_number")
	var businessNumber: String? = null,

	@Column(name = "zip_code", unique = false, nullable = true)
	var zipCode: String? = null,

	@Column(name = "address", unique = false, nullable = true)
	var address: String? = null,

	@Column(name = "address_detail")
	var addressDetail: String? = null,

	@Column(name = "phone1")
	var phone1: String? = null,

	@Column(name = "phone2")
	var phone2: String? = null,

	@Column(name = "phone3")
	var phone3: String? = null,

	@Column(name = "title", unique = false, nullable = true)
	var title: String? = null,

	@Column(name = "introduction", unique = false, nullable = true)
	var introduction: String? = null,

	@Column(name = "information", unique = false, nullable = true)
	var information: String? = null,

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type", unique = false, nullable = false)
	var serviceType: ServiceType? = null,

	@Column(name = "online_yn", unique = false, nullable = true)
	var onlineYn: String? = null,

	@Column(name = "online_date", unique = false, nullable = true)
	var onlineDate: LocalDateTime? = null,

	@Column(name = "city", unique = false, nullable = true)
	var city: String? = null,

	@Column(name = "district", unique = false, nullable = true)
	var district: String? = null,

	@Column(name = "latitude", unique = false, nullable = true)
	var latitude: Double? = null,

	@Column(name = "longitude", unique = false, nullable = true)
	var longitude: Double? = null,

	@Column(name = "ceo_id", unique = false, nullable = true)
	var ceoId: Long? = null,

	@Column(name = "homepage", unique = false, nullable = true)
	var homepage: String? = null,

	@Column(name = "blog", unique = false, nullable = true)
	var blog: String? = null,

	@Column(name = "instagram", unique = false, nullable = true)
	var instagram: String? = null,

	@Column(name = "facebook", unique = false, nullable = true)
	var facebook: String? = null,

	@Column(name = "youtube", unique = false, nullable = true)
	var youtube: String? = null,

	@Column(name = "concurrent_appointment", unique = false, nullable = true)
	var concurrentAppointment: Long? = null,

	@Column(name = "start_appointment_duplicate", unique = false, nullable = true)
	var startAppointmentDuplicate: String? = null,

	@Column(name = "end_appointment_duplicate", unique = false, nullable = true)
	var endAppointmentDuplicate: String? = null

) : Auditable(), Serializable {

//	constructor(name: String?) : this() {
//		this.name = name
//	}
}