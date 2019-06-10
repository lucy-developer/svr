package io.eb.svr

/**
 * Create by lucy on 2019-05-21
 **/
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@EnableScheduling
//@EnableWebSecurity

@SpringBootApplication//(exclude = arrayOf(SecurityAutoConfiguration::class))
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}