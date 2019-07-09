package io.eb.svr.model.enums

/**
 * Create by lucy on 2019-07-02
 **/

enum class ShopSetting (val type: String, val desc: String, val icon: String, val required: String ) {
	SHOP_INFO("SHOP_INFO", "매장 정보 설정", "aaa.svg", "Y"),
	STORE_OPERATION("STORE_OPERATION", "매장 운영 설정", "bbb.svg", "Y"),
	TREATMENT_RESERVATION("TREATMENT_RESERVATION", "시술 예약 설정", "ccc.svg", "N"),
	HOLIDAY_CLOSED("HOLIDAY_CLOSED", "휴무일 설정", "ddd.svg","N"),
	TREATMENT_PRODUCT("TREATMENT_PRODUCT", "시술 상품 관리", "ddd.svg", "Y"),
	MANAGEMENT_STAFF("MANAGEMENT_STAFF", "직원 관리", "eee.svg","N"),
	CUSTOMER_RATING("CUSTOMER_RATING", "고객 등급 관리", "fff.svg","N"),
	PAID_PAYMENT("PAID_PRODUCT", "EB 유료 상품 관리", "ggg.svg","N"),
	ONLINE_BOOKING("ONLINE_BOOKING", "온라인 예약 관리", "hhh.svg","N")
}