package io.eb.svr.model.entity

import org.springframework.security.core.GrantedAuthority

enum class UserRole : GrantedAuthority {
    ADMIN,
    CLIENT,
    OPERATOR,
    CEO,
    LOADER;

    override fun getAuthority(): String = name
}
