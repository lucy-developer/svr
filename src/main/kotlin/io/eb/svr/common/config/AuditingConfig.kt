package io.eb.svr.common.config

import io.eb.svr.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

/**
 * Create by lucy on 2019-07-15
 **/
@Configuration
@EnableJpaAuditing
class AuditingConfig {
	@Bean
	fun auditorProvider(): AuditorAware<String> {
		return SpringSecurityAuditorAwareImpl()
	}
}

class SpringSecurityAuditorAwareImpl : AuditorAware<String> {
	override fun getCurrentAuditor(): Optional<String> {
		val authentication = SecurityContextHolder.getContext().authentication

		if (authentication == null || !authentication.isAuthenticated || authentication is AnonymousAuthenticationToken) {
			return Optional.empty()
		}

		val userPrincipal = authentication.principal as UserPrincipal

		return Optional.of(userPrincipal.id.toString())
//		return Optional.of(authentication.name)
	}

}