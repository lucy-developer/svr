package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.eb.svr.common.util.DateUtil
import io.eb.svr.model.enums.BankCode
import io.eb.svr.model.enums.Position
import io.eb.svr.model.enums.ShopRole
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JoinColumnOrFormula
import org.hibernate.annotations.JoinFormula
import org.hibernate.annotations.Where
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(schema = "customer", name = "b2b_user_store")
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class B2BUserShop (

	@EmbeddedId
	var b2BUserShopPK: B2BUserShopPK,

	@Column(name = "delete_yn", unique = false, nullable = true)
	var deleteYn: String? = "N",

	@Enumerated(EnumType.STRING)
	@Column(name = "position", unique = false, nullable = true)
	var position: Position? = null,

	@Column(name = "confirm_yn")
	var confirmYn: String? = "N",

	@Column(name = "join_date", unique = false, nullable = true)
	var joinDate: LocalDate? = null,

	@Column(name = "leave_date", unique = false, nullable = true)
	var leaveDate: LocalDate? = DateUtil.stringToLocalDate("9999-12-31"),

	@Column(name = "nick_name", unique = false, nullable = true)
	var nickName: String? = null,

	@Enumerated(EnumType.STRING)
	@Column(name = "salary_bank", unique = false, nullable = true)
	var salaryBank: BankCode? = null,

	@Column(name = "salary_bank_number", unique = false, nullable = true)
	var salaryBankNumber: String? = null,

	@Enumerated(EnumType.STRING)
	@Column(name = "shop_role", unique = false, nullable = false)
	var shopRole: ShopRole = ShopRole.STAFF

): Auditable(), Serializable {

	@Embeddable
	data class B2BUserShopPK (
		@Column(name="user_id", nullable=false, insertable = false, updatable = false)
		var userId: Long,

		@Column(name="store_id", nullable=false)
		var storeId: Long
	) : Serializable {

	}
//	constructor() : this(null, "")

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName="id", insertable = false, updatable = false)
	var user: User? = null

	@OneToOne
	@JoinColumn(name = "store_id", referencedColumnName="id", insertable = false, updatable = false)
	@Where(clause = "delete_yn='N'")
	var store: Store? = null

//	@OneToOne
//	@JoinColumnOrFormula(
//		formula = JoinFormula(value = "")
//	)


}