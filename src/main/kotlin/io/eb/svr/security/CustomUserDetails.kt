package io.eb.svr.security

import io.eb.svr.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import io.eb.svr.model.repository.UserRepository
import mu.KLogging
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetails : UserDetailsService {
    companion object : KLogging()

    @Autowired
    private lateinit var userRepository: UserRepository

//    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        logger.info{ "loadUserByUsername username: " + username }
        userRepository.findUsersByEmail(username)?.let { profile ->
            logger.info{ "loadUserByUsername find email: " + profile.email }
            return UserPrincipal(profile)
//            return org.springframework.security.core.userdetails.User
//                .withUsername(profile.id.toString())
//                .password(profile.password)
//                .authorities(profile.role.name)
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false)
//                .build()
        }

        logger.info{ "loadUserByUsername find not email: " + username }
        throw UsernameNotFoundException("User '$username' not found")
//        return UserPrincipal(profile)

    }

//    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User '$id' not found")
        }
        return UserPrincipal(user)
    }

}
