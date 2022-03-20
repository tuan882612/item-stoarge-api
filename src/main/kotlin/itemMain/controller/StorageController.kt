package itemMain.controller

import itemMain.dataLayer.Storage
import itemMain.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/storage")
class StorageController(private val service: ItemService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoneFound(exception: NoSuchElementException): ResponseEntity<String>
        = ResponseEntity(exception.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(exception: IllegalArgumentException): ResponseEntity<String>
        = ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun retrieveItems(): Collection<Storage> = service.retrieveItems()

    @GetMapping("/{id}")
    fun retrieveItem(@PathVariable id: Int): Storage = service.getItem(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addItem(@RequestBody item: Storage): Storage = service.addItem(item)

    @PatchMapping
    fun updateStorage(@RequestBody item: Storage): Storage = service.updateItem(item)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItem(@PathVariable id: Int): Unit = service.deleteItem(id)
}