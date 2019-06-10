package io.eb.svr.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import io.eb.svr.model.repository.B2BUserRepository

@Service
class CustomUserDetails : UserDetailsService {
    @Autowired
    private lateinit var b2BUserRepository: B2BUserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val profile = b2BUserRepository.findById(username)
            ?: throw UsernameNotFoundException("User '$username' not found")

        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(profile.password)
            .authorities(profile.role)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }

}
