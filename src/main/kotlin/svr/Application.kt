package io.eb.svr

/**
 * Create by lucy on 2019-05-21
 **/
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
//open class Application {
//	companion object {
//		@JvmStatic fun main(args: Array<String>) {
//			runApplication<Application>(*args)
////	println("hello")
//		}
//	}
//}

class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


