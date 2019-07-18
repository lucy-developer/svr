package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.eb.svr.common.util.DateUtil
import io.eb.svr.model.enums.BankCode
import io.eb.svr.model.enums.EmployeeStatus
import io.eb.svr.model.enums.Position
import io.eb.svr.model.enums.ShopRole
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Where
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

/**
 * Create by lucy on 2019-07-15
 **/
@Entity
@Table(schema = "customer",
	name = "employee_history",
	uniqueConstraints = [
		UniqueConstraint(columnNames = ["user_id", "shop_id", "end_date", "start_date"])])
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmployeeHistory (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq", updatable = false)
	var seq: Long,

	@Column(name="user_id", nullable=false, insertable = true, updatable = false)
	var userId: Long,

	@Column(name="shop_id", nullable=false, insertable = true, updatable = false)
	var shopId: Long,

	@Column(name = "start_date", nullable = true, insertable = true, updatable = false)
	var startDate: LocalDate? = null,

	@Column(name = "end_date", nullable=false)
	var endDate: LocalDate = DateUtil.stringToLocalDate("9999-12-31"),

	@Column(name = "delete_yn", unique = false, nullable = true)
	var deleteYn: String? = "N",

	@Enumerated(EnumType.STRING)
	@Column(name = "status", unique = false, nullable = true)
	var status: EmployeeStatus? = null

): Auditable(), Serializable {

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName="id", insertable = false, updatable = false)
	var user: User? = null

	@OneToOne
	@JoinColumn(name = "shop_id", referencedColumnName="id", insertable = false, updatable = false)
	@Where(clause = "delete_yn='N'")
	var store: Store? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as EmployeeHistory

		if ( (userId != other.userId) &&
			 (shopId != other.shopId) &&
			 (startDate != other.startDate) &&
			 (endDate != other.endDate)	) return false

		return true
	}

	override fun hashCode(): Int {
		return this.userId.hashCode() * 41 +
				this.shopId.hashCode() * 41 +
				this.endDate.hashCode() * 17 +
				this.startDate.hashCode() * 17
	}

}