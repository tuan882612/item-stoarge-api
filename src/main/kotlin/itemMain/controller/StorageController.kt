package itemMain.controller

import itemMain.dataLayer.Storage
import itemMain.service.ItemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/storage")
class StorageController(private val service: ItemService) {
    @GetMapping
    fun retrieveItems(): Collection<Storage> = service.retrieveItems()

    @GetMapping("/{id}")
    fun retrieveItem(@PathVariable id: Int): Storage = service.getItem(id)
}