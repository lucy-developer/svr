package io.eb.svr.security.jwt

import mu.KLogging
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import kotlin.math.log

class JwtConfigurerAdapter(
    private val tokenProvider: JwtTokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    companion object : KLogging()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(
            JwtTokenFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}
