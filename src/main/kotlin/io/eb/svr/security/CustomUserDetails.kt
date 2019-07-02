package io.eb.svr.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import io.eb.svr.model.repository.UserRepository
import mu.KLogging

@Service
class CustomUserDetails : UserDetailsService {
    companion object : KLogging()

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        logger.info{ "loadUserByUsername username: " + username }

        val profile = userRepository.findUsersByEmail(username)
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
