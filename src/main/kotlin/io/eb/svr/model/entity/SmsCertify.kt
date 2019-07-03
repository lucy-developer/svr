package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Data
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-06-14
 **/
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(schema = "customer", name="sms_certify")
data class SmsCertify (
	@EmbeddedId
	var pk: SmsCertifyPk,

	@Column(name = "count")
	var count: Long,

	@Column(name = "cert_number")
	var certNumber: String


) : Auditable(), Serializable {

	@Embeddable
	data class SmsCertifyPk (
		@Column(name = "date")
		var date: String,

		@Column(name = "mobile")
		var mobile: String
	) : Serializable {

	}
}
