package io.eb.svr.security.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import io.eb.svr.exception.CustomException

import io.eb.svr.security.CustomUserDetails
import io.eb.svr.common.config.SecurityConfig.TOKEN_EXPIRATION_TIME
import io.eb.svr.common.config.SecurityConfig.TOKEN_HEADER
import io.eb.svr.common.config.SecurityConfig.TOKEN_SECRET_KEY
import io.eb.svr.common.config.SecurityConfig.TOKEN_TYPE
import io.eb.svr.model.entity.UserRole
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    // FIXME: Get secret key from the configuration server.
//    @Value("\${security.jwt.token.secret-key:$TOKEN_SECRET_KEY}")
//    private lateinit var secretKey: String
//
//    @Value("\${security.jwt.token.expire-length:$TOKEN_EXPIRATION_TIME}")
//    private var expirationTime: Long = -1
    private var secretKey = TOKEN_SECRET_KEY
    private var expirationTime = TOKEN_EXPIRATION_TIME

    @Autowired
    private lateinit var userDetails: CustomUserDetails

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String, role: UserRole): String {
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = SimpleGrantedAuthority(role.authority)

        val now = Date()
        val expiration = Date(now.time + expirationTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    @Throws(CustomException::class)
    fun getAuthenticationOrThrow(token: String): Authentication {
        val username = getUsernameOrThrow(token)
        val details = userDetails.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(userDetails, "", details.authorities)
    }

    @Throws(CustomException::class)
    fun getUsernameOrThrow(
        token: String
    ): String = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        ?: throw CustomException("Invalid token", UNAUTHORIZED)

    fun resolveTokenOrNull(
        request: HttpServletRequest
    ) = request.getHeader(TOKEN_HEADER)?.removePrefix("$TOKEN_TYPE ")

    @Throws(CustomException::class)
    fun resolveTokenOrThrow(
        request: HttpServletRequest
    ) = resolveTokenOrNull(request)
        ?: throw CustomException("Invalid token", UNAUTHORIZED)

    @Throws(CustomException::class)
    fun validateTokenOrThrow(token: String) {
        try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
        } catch (exception: Exception) {
            when (exception) {
                is IllegalArgumentException,
                is JwtException -> throw CustomException("Invalid token", UNAUTHORIZED)
                else -> throw CustomException("Unknown exception", INTERNAL_SERVER_ERROR)
            }
        }
    }
}
