package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-06-13
 **/
enum class ServiceType(val type: String, val desc: String) {
	HAIR("HAIR", "헤어샵"),
	NAIL("NAIL", "네일샵"),
	MASSAGE("MASSAGE", "마사지"),
	ETC("ETC", "기타")
}

enum class ProductType(val type: String, val desc: String) {
	SINGLE("SINGLE", "시술단일상품"),
	PACKAGE("PACKAGE", "패키지상품")
}

enum class ProductCls(val type: String, val desc: String) {
	CUT("CUT", "Cut"),
	PERM("PERM", "Perm"),
	COLOR("COLOR", "Color"),
	CLINIC("CLINIC", "Clinic"),
	STYLING("STYLING", "Styling"),
	MAKEUP("MAKEUP", "Makeup")
}

enum class ProductSaleType(val type: String, val desc: String) {
	TIME("TIME", "타임세일")
}

enum class EventType(val type: String, val desc: String) {
	EVERY_DAY("EVERY_DAY", "매일"),
	EVERY_WEEK("EVERY_WEEK", "매주"),
	PERIOD("PERIOD", "기간")

}

enum class TargetType(val type: String, val desc: String) {
	MALE("MALE", "남성"),
	FEMALE("FEMALE", "여성"),
	ALL("ALL", "공용")

}
