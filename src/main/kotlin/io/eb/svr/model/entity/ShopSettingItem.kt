package io.eb.svr.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.eb.svr.model.enums.ShopSetting
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-07-03
 **/
@Entity
@Table(schema = "core", name = "store_setting_item")
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ShopSettingItem (
	@EmbeddedId
	var shopSettingItemPK: ShopSettingItemPK,

	@Column(name = "description", unique = false, nullable = false)
	var description: String,

	@Column(name = "required", unique = false, nullable = false)
	var required: String = "N",

	@Column(name = "icon", unique = false, nullable = false)
	var icon: String,

	@Column(name = "setting_yn", unique = false, nullable = false)
	var settingYn: String = "N"

): Auditable(), Serializable {

	@Embeddable
	data class ShopSettingItemPK (
		@Column(name="store_id", nullable=false, insertable = false, updatable = false)
		var storeId: Long,

//		@Enumerated(EnumType.STRING)
		@Column(name="item_code", nullable=false)
		var itemCode: String
	) : Serializable {

	}
}