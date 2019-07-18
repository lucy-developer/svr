package io.eb.svr.security

import com.fasterxml.jackson.annotation.JsonIgnore
import io.eb.svr.model.entity.User
import io.eb.svr.model.entity.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

/**
 * Create by lucy on 2019-07-15
 **/
class UserPrincipal(
	val id: Long,

	val name: String,

	private val username: String,

	@JsonIgnore
	val email: String,

	@JsonIgnore
	private val password: String,

	private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails {

	constructor(user: User): this(
		id = user.id,
		name = user.username,
		username = user.username,
		email = user.email,
		password = user.password,
		authorities = Collections.singletonList(SimpleGrantedAuthority(user.role.name))
	)

	override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

	override fun isEnabled(): Boolean = true

	override fun getUsername(): String = username

	override fun getPassword(): String = password

	override fun isCredentialsNonExpired(): Boolean = true

	override fun isAccountNonExpired(): Boolean = true

	override fun isAccountNonLocked(): Boolean = true

	override fun equals(o: Any?): Boolean {
		if (this === o) return true
		if (o == null || javaClass != o.javaClass) return false
		val that = o as UserPrincipal?
		return id == that?.id
	}

	override fun hashCode(): Int {
		return Objects.hash(id)
	}
}

//fun UserPrincipal.isUser(): Boolean {
//	return this.role.equals(UserRole.USER)
//}