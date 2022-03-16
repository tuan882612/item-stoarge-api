package itemMain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class ItemManagerApplication {
	@GetMapping
	fun helloWorld(): String = "Hello this the main output"
}

fun main(args: Array<String>) {
	runApplication<ItemManagerApplication>(*args)
}
