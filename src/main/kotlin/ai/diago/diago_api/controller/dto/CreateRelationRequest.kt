package ai.diago.diago_api.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import tools.jackson.databind.JsonNode
import tools.jackson.databind.node.JsonNodeFactory

data class CreateRelationRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val from: String,

    @field:NotBlank
    @field:Size(max = 128)
    val to: String,

    @field:NotBlank
    @field:Size(max = 64)
    val kind: String,

    val details: JsonNode = JsonNodeFactory.instance.objectNode(),
)
