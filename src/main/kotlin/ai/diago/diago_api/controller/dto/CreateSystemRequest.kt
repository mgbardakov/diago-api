package ai.diago.diago_api.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.Valid

data class CreateSystemRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val name: String,

    @field:Valid
    // TODO: Add request validation to ensure node names are unique within a system.
    val nodes: List<CreateNodeRequest> = emptyList(),

    @field:Valid
    val relations: List<CreateRelationRequest> = emptyList(),
)
