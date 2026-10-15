package ai.diago.diago_api.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.Valid
import ai.diago.diago_api.controller.dto.validation.UniqueNodeNames

data class CreateSystemRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val name: String,

    @field:Valid
    @field:UniqueNodeNames
    val nodes: List<CreateNodeRequest> = emptyList(),

    @field:Valid
    val relations: List<CreateRelationRequest> = emptyList(),
)
