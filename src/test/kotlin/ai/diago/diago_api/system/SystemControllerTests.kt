package ai.diago.diago_api.system

import ai.diago.diago_api.entity.SystemRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTests(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val systemRepository: SystemRepository,
    @Autowired private val jdbcTemplate: JdbcTemplate,
) {
    @BeforeEach
    fun clearDatabase() {
        systemRepository.deleteAll()
    }

    @Test
    fun `creates a system`() {
        mockMvc.perform(
            post("/systems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "name": "shop",
                      "nodes": [
                        {
                          "name": "orders",
                          "kind": "GENERATED_API",
                          "spec": {
                            "description": "Manages orders.",
                            "guarantees": ["Orders are created only once."],
                            "openApi": { "openapi": "3.1.0", "paths": {} }
                          }
                        },
                        {
                          "name": "inventory",
                          "kind": "GENERATED_API",
                          "spec": {
                            "description": "Manages inventory.",
                            "guarantees": ["Availability is returned for a product."],
                            "openApi": { "openapi": "3.1.0", "paths": {} }
                          }
                        }
                      ],
                      "relations": [
                        {
                          "from": "orders",
                          "to": "inventory",
                          "kind": "REST",
                          "details": { "operation": "getAvailability" }
                        }
                      ]
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").isNumber)
            .andExpect(jsonPath("$.name").value("shop"))
            .andExpect(jsonPath("$.status").value("PENDING"))

        kotlin.test.assertEquals(1, systemRepository.count())
        kotlin.test.assertEquals(2, jdbcTemplate.queryForObject("select count(*) from node", Long::class.java))
        kotlin.test.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from relation", Long::class.java))
    }

    @Test
    fun `rejects generated api without openapi`() {
        mockMvc.perform(
            post("/systems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "name": "shop",
                      "nodes": [
                        {
                          "name": "orders",
                          "kind": "GENERATED_API",
                          "spec": {
                            "description": "Manages orders.",
                            "guarantees": ["Orders are created only once."]
                          }
                        }
                      ]
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isBadRequest)

        kotlin.test.assertEquals(0, systemRepository.count())
    }

    @Test
    fun `rejects duplicate node names`() {
        mockMvc.perform(
            post("/systems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "name": "shop",
                      "nodes": [
                        { "name": "orders", "kind": "POSTGRES" },
                        { "name": " orders ", "kind": "POSTGRES" }
                      ]
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isBadRequest)

        kotlin.test.assertEquals(0, systemRepository.count())
    }

    @Test
    fun `rejects invalid relation graph`() {
        mockMvc.perform(
            post("/systems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "name": "shop",
                      "nodes": [
                        { "name": "orders", "kind": "POSTGRES" },
                        { "name": "inventory", "kind": "POSTGRES" }
                      ],
                      "relations": [
                        { "from": "orders", "to": "inventory", "kind": "REST" }
                      ]
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isBadRequest)

        kotlin.test.assertEquals(0, systemRepository.count())
    }
}
