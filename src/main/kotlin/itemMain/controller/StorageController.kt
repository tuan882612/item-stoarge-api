package itemMain.controller

import itemMain.dataLayer.Storage
import itemMain.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/storage")
class StorageController(private val service: ItemService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun noHandleFound(exception: NoSuchElementException): ResponseEntity<String> = ResponseEntity(exception.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun retrieveItems(): Collection<Storage> = service.retrieveItems()

    @GetMapping("/{id}")
    fun retrieveItem(@PathVariable id: Int): Storage = service.getItem(id)
}