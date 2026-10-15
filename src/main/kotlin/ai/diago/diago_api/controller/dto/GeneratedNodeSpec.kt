package ai.diago.diago_api.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import tools.jackson.databind.JsonNode

data class GeneratedNodeSpec(
    @field:NotBlank
    val description: String,

    @field:NotEmpty
    val guarantees: List<@NotBlank String>,

    val openApi: JsonNode? = null,
)
