package itemMain.dataSource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockItemDataSourceTest {
    private val mockData = MockItemDataSource()

    @Test
    fun `should provide a collection of items`(){
        val item = mockData.retrieveItems()
        assertThat(item.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`(){
        val item = mockData.retrieveItems()
        assertThat(item).allMatch { it.name.isNotBlank() }
        assertThat(item).anyMatch { it.id != 0 }
        assertThat(item).allMatch { it.number.isNotBlank() }
    }
}