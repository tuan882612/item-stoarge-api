package itemMain.controller

import com.fasterxml.jackson.databind.ObjectMapper
import itemMain.dataLayer.Storage
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
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class StorageControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired var objectMapper: ObjectMapper
    )
{
    val baseUrl = "/api/storage"

    @Nested
    @DisplayName("GET /api/storage")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveItems {

        @Test
        fun `Return all items`(){
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
    @DisplayName("GET /api/storage/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveItem {

        @Test
        fun `Returns item with the given id`(){
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
        fun `Returns NOT FOUND if the id does not exist`(){
            val id = "-1"
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `Returns BAD REQUEST if the id does not match format`(){
            val id = "w"
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("POST /api/storage")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddItem {

        @Test
        fun `Adds new item to storage`(){
            val item = Storage(999, "test", "9999")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper
            }
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }
        }
    }
}