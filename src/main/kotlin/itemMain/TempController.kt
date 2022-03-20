package itemMain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/outputTest")
class TempController {

    @GetMapping
    fun output() = "This the output for the endpoint"
}
