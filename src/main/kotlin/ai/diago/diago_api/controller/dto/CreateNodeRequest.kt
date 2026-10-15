package ai.diago.diago_api.controller.dto

import ai.diago.diago_api.entity.NodeKind
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateNodeRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val name: String,

    val kind: NodeKind,
)
