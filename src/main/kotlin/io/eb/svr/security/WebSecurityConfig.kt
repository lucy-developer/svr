package io.eb.svr.security

import io.eb.svr.common.config.ApiConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import io.eb.svr.security.jwt.JwtConfigurerAdapter
import io.eb.svr.security.jwt.JwtTokenProvider
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.AUTH_PATH
import io.eb.svr.common.config.ApiConfig.CORE_PATH
import io.eb.svr.common.config.SecurityConfig.PASSWORD_STRENGTH

@Configuration
//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
////        cors()
//
////        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//    http
//        .authorizeRequests()
//            .antMatchers(HttpMethod.POST,"/$API_VERSION/$AUTH_PATH/b2bLogin").permitAll()
//            .antMatchers("/test").permitAll()
//            .anyRequest().authenticated()
//        .and()
//        .cors()
////        exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
//        .and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//        .httpBasic()
//        .and()
//        .csrf().disable()
//
//        http.apply(JwtConfigurerAdapter(tokenProvider))
//    }

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity): Unit = with(http) {
		csrf().disable()

		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		authorizeRequests()
			.antMatchers("/$API_VERSION/$AUTH_PATH/**").permitAll()
			.antMatchers("/$API_VERSION/$CORE_PATH/**").permitAll()
			.anyRequest().authenticated()

		apply(JwtConfigurerAdapter(tokenProvider))
	}

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(PASSWORD_STRENGTH)

    @Bean
    fun authManager(): AuthenticationManager = authenticationManagerBean()

//    @Bean
//    open fun unauthorizedEntryPoint(): AuthenticationEntryPoint {
//        return AuthenticationEntryPoint { request, response, authException -> response.sendError(
//            HttpStatus.UNAUTHORIZED.value(),
//            HttpStatus.UNAUTHORIZED.getReasonPhrase()) }
//    }

//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    override
//    @Bean(name = arrayOf("authenticationManagerBean"))
//    @Throws(Exception::class)
//    fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }
//
//    @Configuration
//    protected class AuthenticationConfiguration(
//        private val userDetailsService: CustomUserDetails
//    )  : GlobalAuthenticationConfigurerAdapter(){
//
//        @Throws(Exception::class)
//        override fun init(auth: AuthenticationManagerBuilder) {
//            auth.userDetailsService(userDetailsService)
//                .passwordEncoder(BCryptPasswordEncoder())
//        }
//    }
//
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = Arrays.asList("http://dredear.nolit.net", "http://localhost:4080")
//        configuration.allowedMethods = Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE")
//        configuration.allowedHeaders = Arrays.asList("*")
//        configuration.allowCredentials = true
//        configuration.maxAge = 3600
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
}
