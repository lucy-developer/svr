package io.eb.svr.security.jwt

import mu.KLogging
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.log

//class JwtConfigurerAdapter(
//    private val tokenProvider: JwtTokenProvider
//) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
//
//    companion object : KLogging()
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.addFilterBefore(
//            JwtTokenFilter(tokenProvider),
//            UsernamePasswordAuthenticationFilter::class.java
//        )
//    }
//}

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {

    companion object : KLogging()

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        logger.error("Responding with unauthorized error. Message - {}", authException?.message)
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException?.message)
    }

}