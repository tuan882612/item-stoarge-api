package itemMain.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import itemMain.dataSource.ItemDataSource
import org.junit.jupiter.api.Test

internal class ItemServiceTest {
    private val dataSource: ItemDataSource = mockk(relaxed = true)
    private val itemService = ItemService(dataSource)

    @Test
    fun `Data source called to retrieve items`(){
        itemService.retrieveItems()
        verify(exactly = 1) { dataSource.retrieveItems() }
    }
}