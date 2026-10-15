package ai.diago.diago_api.controller.dto

import ai.diago.diago_api.entity.RelationKind
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

    val kind: RelationKind,

    val details: JsonNode = JsonNodeFactory.instance.objectNode(),
)
