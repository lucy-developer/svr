package io.eb.svr.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import io.eb.svr.exception.CustomException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@Component
class JwtTokenFilter(
    private val tokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        try {
            val token = tokenProvider.resolveTokenOrNull(request)
            if (token != null) {
                tokenProvider.validateTokenOrThrow(token)
                SecurityContextHolder.getContext().authentication = tokenProvider.getAuthenticationOrThrow(token)
            }
        } catch (exception: CustomException) {
            SecurityContextHolder.clearContext()
            response.sendError(exception.status.value(), exception.message)
            return
        }

        chain.doFilter(request, response)
    }

}
