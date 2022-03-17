package itemMain.dataSource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockItemDataSourceTest {
    private val mockData = MockItemDataSource()

    @Test
    fun `Returns collection of items`(){
        val item = mockData.retrieveItems()
        assertThat(item.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `Data is not missing`(){
        val item = mockData.retrieveItems()
        assertThat(item).allMatch { it.name.isNotBlank() }
        assertThat(item).anyMatch { it.id != 0 }
        assertThat(item).allMatch { it.number.isNotBlank() }
    }
}