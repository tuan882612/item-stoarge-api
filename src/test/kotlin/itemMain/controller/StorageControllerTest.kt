package itemMain.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance

@SpringBootTest
@AutoConfigureMockMvc
internal class StorageControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    val baseUrl = "/api/storage"

    @Nested
    @DisplayName("retrieveItems()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveItems {
        @Test
        fun `should return all items`(){
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id"){ value("1") }
                }
        }
    }

    @Nested
    @DisplayName("retrieveItem()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveItem {
        @Test
        fun `should return the item with the given id`(){
            val id = 1
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.number") { value("0001")}
                    jsonPath("$.name") { value("omg")}
                }
        }

        @Test
        fun `should return NOT FOUND if the id does not exist`(){
            val id = "does_not_exist"
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }


}