package itemMain.controller

import com.fasterxml.jackson.databind.ObjectMapper
import itemMain.dataLayer.Storage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StorageControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    var objectMapper: ObjectMapper
    )
{
    val baseUrl = "/api/storage"
    val baseId = 1
    val dne = -1

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
                    jsonPath("$[0].id"){ value(baseId) }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/storage/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveItem {

        @Test
        fun `Returns item with the given id`(){
            val item = Storage(baseId, "0001", "omg")
            mockMvc.get("$baseUrl/${item.id}")
                .andDo { print() }
                .andExpect { status { isOk() } }
        }

        @Test
        fun `Returns NOT FOUND if the id does not exist`(){
            mockMvc.get("$baseUrl/$dne")
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
            val newItem = Storage(999, "test", "9999")
            val tryPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newItem)
            }
            tryPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newItem))
                    }
                }

            mockMvc.get("$baseUrl/${newItem.id}")
                .andExpect { content { json(objectMapper.writeValueAsString(newItem)) } }
        }

        @Test
        fun `Returns BAD REQUEST if id already in storage`(){
            val invalid = Storage(baseId, "omg", "0001")
            val tryPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalid)
            }

            tryPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH /api/storage")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateItem {

         @Test
         fun `Updates an existing item`(){
             val updatedItem = Storage(baseId, "omg", "0013")
             val performPatch = mockMvc.patch(baseUrl) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(updatedItem)
             }

             performPatch
                 .andDo { print() }
                 .andExpect {
                     status { isOk() }
                     content {
                         contentType(MediaType.APPLICATION_JSON)
                         json(objectMapper.writeValueAsString(updatedItem))
                     }
                 }

             mockMvc.get("$baseUrl/${updatedItem.id}")
                 .andExpect { content { json(objectMapper.writeValueAsString(updatedItem)) } }
         }

        @Test
        fun `Returns BAD REQUEST if item with given id does not exist`(){
            val item = Storage(dne, "test", "9999")
            val performPatch = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(item)
            }

            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/storage/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteItem {

        @Test
        fun `Deletes item with given id`() {
            mockMvc.delete("$baseUrl/$baseId")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("$baseUrl/$baseId")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `Returns NOT FOUND item with id does not exist`(){
            mockMvc.delete("$baseUrl/$dne")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}