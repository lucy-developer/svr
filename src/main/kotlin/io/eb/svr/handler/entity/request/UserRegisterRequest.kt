package io.eb.svr.handler.entity.request

import io.eb.svr.model.enums.BankCode
import io.eb.svr.model.enums.Gender
import io.eb.svr.model.enums.Position
import io.eb.svr.model.enums.ShopRole
import java.time.LocalDate
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-07-01
 **/
data class UserRegisterRequest (
	@NotNull
	val email: String,

	@NotNull
	val mobile1: String,

	@NotNull
	val mobile2: String,

	@NotNull
	val mobile3: String,

	@NotNull
	val name: String,

	@NotNull
	val password: String,

	val id: Long? = -1,
	val sex: Gender? = null,

	val storeId: Long? = null,
	val nickName: String? = null,
	@Enumerated(EnumType.STRING) val position: Position? = null,
	val joinDate: LocalDate? = null,

	@Enumerated(EnumType.STRING) val salaryBankCode: BankCode? = null,
	val salaryBankNum: String? = null,

	@Enumerated(EnumType.STRING) val role: ShopRole? = null

)