package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(
	schema = "customer", name = "user",
	indexes = [
		Index(columnList = "email", unique = true)
	]
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class User (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	var id: Long,

	@Size(min = 6, max = 255, message = "Invalid email length: interval is [4, 255]")
	@Column(name = "email", unique = true, nullable = false, updatable = false, insertable = true)
	var email: String,

	@get:JsonIgnore
	@field:Size(min = 8, message = "Invalid password length: interval is [8, 255]")
	@field:Column(name = "password", unique = false, nullable = false)
	var password: String,

	@Size(min = 2, max = 255, message = "Invalid name length: interval is [2, 255]")
	@Column(name = "username", unique = false, nullable = false)
	var username: String,

	@Size(min = 2, max = 5, message = "Invalid mobile1 length: interval is [2, 5]")
	@Column(name = "mobile1", unique = false, nullable = false)
	var mobile1: String,

	@Size(min = 2, max = 5, message = "Invalid mobile2 length: interval is [2, 5]")
	@Column(name = "mobile2", unique = false, nullable = false)
	var mobile2: String,

	@Size(min = 2, max = 5, message = "Invalid mobile3 length: interval is [2, 5]")
	@Column(name = "mobile3", unique = false, nullable = false)
	var mobile3: String,

	@Column(name = "nickname", unique = false, nullable = true)
	var nickName: String? = null,

	@Column(name = "sex", unique = false, nullable = true)
	var sex: String? = null,

	@Column(name = "birthday", unique = false, nullable = true)
	var birthday: Date? = null,

//	@Enumerated(EnumType.STRING)
	@Size(max = 10)
	@Column(name = "role", unique = false, nullable = false)
	var role: UserRole

) : Auditable(), Serializable {

//
//)
//	constructor() {
//
//	}

//	constructor(id: String,
//				name: String,
//				password: String,
//				sex: String,
//	            role: String
//	) : this() {
//		this.id = id
//		this.name = name
//		this.password = password
//		this.sex = sex
//		this.role = role
//
//	}

}