package io.eb.svr.model.entity

import io.eb.svr.model.enums.ServiceType
import org.hibernate.annotations.DynamicInsert
import java.io.Serializable
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

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "business_number")
	var businessNumber: String? = null,

	@Column(name = "zip_code")
	var zipCode: String? = null,

	@Column(name = "address")
	var address: String? = null,

	@Column(name = "address_detail")
	var addressDetail: String? = null,

	@Column(name = "phone1")
	var phone1: String? = null,

	@Column(name = "phone2")
	var phone2: String? = null,

	@Column(name = "phone3")
	var phone3: String? = null,

	@Column(name = "title")
	var title: String? = null,

	@Column(name = "information")
	var information: String? = null,

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type")
	var serviceType: ServiceType? = null,

	@Column(name = "online_yn")
	var onlineYn: String? = null,

	@Column(name = "city")
	var city: String? = null,

	@Column(name = "district")
	var district: String? = null

) : Auditable(), Serializable {

//	constructor(name: String?) : this() {
//		this.name = name
//	}
}