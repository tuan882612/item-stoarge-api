package itemMain.dataSource.mock

import itemMain.dataLayer.Storage
import itemMain.dataSource.ItemDataSource
import org.springframework.stereotype.Repository

@Repository
class MockItemDataSource: ItemDataSource {
    val items = mutableListOf(
        Storage(1, "omg", "0001"),
        Storage(2, "okay", "0012"),
        Storage(3, "wow", "0023"),
    )

    override fun retrieveItems(): Collection<Storage> = items

    override fun retrieveItem(id: Int): Storage =
        items.firstOrNull() { it.id == id } ?: throw NoSuchElementException("No such item with id: $id exist in storage")

    override fun createItem(item: Storage): Storage {
        if(items.any { it.id == item.id}) {
            throw IllegalArgumentException("Item ${item.id} already exist in storage")
        }
        items.add(item)
        return item
    }

    override fun updateItem(item: Storage): Storage {
        val curItem = items.firstOrNull { it.id == item.id } ?: throw NoSuchElementException("Could not find item with id ${item.id}")
        items.remove(curItem)
        items.add(item)
        return item
    }
}