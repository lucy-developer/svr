package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.eb.svr.model.enums.StplatType
import lombok.Data
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-06-12
 **/
@Entity
@Table(schema = "public", name = "splat")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Stplat (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq", updatable = false)
	var id: Long,

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	var type: StplatType,

	@Column(name="description")
	var description: String

) : Auditable(), Serializable {

}