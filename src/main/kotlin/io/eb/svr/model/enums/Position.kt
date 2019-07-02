package io.eb.svr.model.enums

import org.springframework.security.core.GrantedAuthority

enum class Position (val type: String, val desc: String) {
    CEO("CEO", "대표"),
    DIRECTOR("DIRECTOR", "원장"),
    DEPUTY_DIRECTOR("DEPUTY_DIRECTOR", "부원장"),
    HEAD_DEPARTMENT("HEAD_DEPARTMENT", "실장"),
    TEAM_LEADER("TEAM_LEADER", "팀장"),
    MANAGER("MANAGER", "매니저"),
    CHIEF_DESIGNER("CHIEF_DESIGNER", "수석디자이너"),
    DESIGNER("DESIGNER", "디자이너"),
    STYLIST("STYLIST", "스타일리스트"),
    STAFF("STAFF", "스텝"),
    ETC("ETC", "기타")
}

enum class ShopRole (val type: String, val desc: String) {
    ADMIN("ADMIN", "관리자"),
    MANAGER("MANAGER", "매니저"),
	EXPERT("EXPERT", "전문가"),
    STAFF("STAFF", "직원"),
    ETC("ETC", "기타")
}