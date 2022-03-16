package itemMain.dataSource.mock

import itemMain.dataLayer.Storage
import itemMain.dataSource.ItemDataSource
import org.springframework.stereotype.Repository

@Repository
class MockItemDataSource: ItemDataSource {
    val item = listOf(
        Storage(1, "omg", "0001"),
        Storage(2, "okay", "0012"),
        Storage(3, "wow", "0023"),
    )

    override fun retrieveItems(): Collection<Storage> = item

    override fun retrieveItem(id: Int): Storage = item.first { it.id == id }
}