package itemMain.service

import itemMain.dataLayer.Storage
import itemMain.dataSource.ItemDataSource
import org.springframework.stereotype.Service

@Service
class ItemService(private val dataSource: ItemDataSource) {

    fun retrieveItems(): Collection<Storage> = dataSource.retrieveItems()

    fun getItem(id: Int): Storage = dataSource.retrieveItem(id)

    fun addItem(item: Storage): Storage = dataSource.createItem(item)

    fun updateItem(item: Storage): Storage = dataSource.updateItem(item)

    fun deleteItem(id: Int): Unit = dataSource.deleteItem(id)
}